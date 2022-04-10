package Backend;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Backend.Play.setMyRanking;

public class Database {

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables & lists
     * --------------------------------------------------------------------------------
     */

    //1.1 Primitive variables
    private int score;
    private int time;
    private int position;
    private int DBid; //ID of row in DB
    private String quizType;

    //1.2 String variables
    private String name;
    private String date;
    private String javaid; //This variable is used for extracting specificic information from database
    static public String myRanking; //Position of current quiz in the ranking

    //1.3 Lists
    static public List<Database> scoresFromDB = new ArrayList<>();

    /**
     * --------------------------------------------------------------------------------
     * 2. Constructors
     * --------------------------------------------------------------------------------
     */

    //2.1 Default constructor
    public Database() {
    }

    //2.2 Custom constructor
    public Database(int score, int time, String name, int position, int DBid, String quizType) {
        this.score = score;
        this.time = time;
        this.name = name;
        this.date = Engine.date.toString();
        this.position=position;
        this.DBid=DBid;
        this.quizType=quizType;
    }

    /**
     * --------------------------------------------------------------------------------
     * 3. Methods
     * --------------------------------------------------------------------------------
     */

    //3.1 toString override method
    @Override
    public String toString() {
        return "position= " + position +
                ", score=" + score +
                ", time=" + time +
                ", name='" + name +
                ", date=" + date +
                ", quiz type=" + quizType;
    }

    //3.2 Saving quiz results and details in the database
    public static void saveInDB(String name, LocalDate date, double score, long seconds, String javaid, Play play) {

        try {

            //3.2.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //3.2.2 Preparing the query to add the details in database
            PreparedStatement pSt = conn.prepareStatement("INSERT INTO stats (name,date,score,time,javaid,quizType) VALUES(?,?,?,?,?,?)");
            pSt.setString(1, name);
            pSt.setDate(2, Date.valueOf(date));
            pSt.setDouble(3, score);
            pSt.setLong(4, seconds);
            pSt.setString(5, javaid);
            pSt.setString(6, play.getQuizType());

            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //3.3 Fetching data from database
    public static void getFromDB() {

        try {

            //3.3.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //3.3.2 Preparing the query that will extract the data
            PreparedStatement pSt = conn.prepareStatement("select name,score,time,date,dbid,quizType from stats");

            //3.3.3 Execute query
            ResultSet rs = pSt.executeQuery();

            //3.3.4 Clearing existing list as quiz results are re-ranked each time method is called
            scoresFromDB.clear();

            //3.3.5 Execute query as long as there are still items in database
            while (rs.next()) {

                //3.3.5.1 Transfer database items into a list (this is used for sorting the items from scoresFromDB list)
                Play item = new Play();
                item.setName(rs.getString("name"));
                item.setScore(rs.getInt("score"));
                item.setTime(rs.getInt("time"));
                item.setDate(rs.getString("date"));
                item.setDBid(rs.getInt("dbid"));
                item.setQuizType(rs.getString("quizType"));
                Play.scoresFromDB.add(item);
            }

            rs.close();
            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //3.4 Sorting list with database items according to score(most criterion) and time(second criterion)
    public static void sortBackendList() {

        //3.4.1 Fetch data from database
        getFromDB();

        //3.4.2 Sort items from database in scoresFromDB list according to score and time
        Comparator<Play> comparator = Comparator.comparing(Play::getScore, Comparator.reverseOrder());
        comparator = comparator.thenComparing(Play::getTime);
        Play.scoresFromDB.sort(comparator);

        //3.4.3 Re-setting the position of the items according to the new sortation
        for (int i = 0; i < Play.scoresFromDB.size(); i++) {
            Play.scoresFromDB.get(i).setPosition(i);
        }
    }

    //3.5 Updating the database with the newly sorted items from sortFromDB list
    public static void updateDatabaseRanking() {

        //3.5.1 Counter used for iterating the DB update query below
        int row = 0;

        //3.5.2 Call method to sort list
        sortBackendList();


        try {

            //3.5.3 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /*3.5.4 Update new positions for each item based on the new sortation. In order to find where to introduce
                    the position for each row, DBid is used, which was previously fetched before the sortation in
                    getFromDB*/
            while (row < Play.scoresFromDB.size()) {
                PreparedStatement pSt = conn.prepareStatement("UPDATE stats SET position=? WHERE dbid=? AND quizType=?");
                pSt.setInt(1, Play.scoresFromDB.get(row).getPosition());
                pSt.setInt(2, Play.scoresFromDB.get(row).getDBid());
                pSt.setString(3,Play.scoresFromDB.get(row).getQuizType());
                row++;
                pSt.executeUpdate();
                pSt.close();
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    //3.6 Calculate ranking for current quiz trial
    public static void calculateMyRank(Play play) {

        //3.6.1 Get total number of items in database as total ranks
        String totalRanks = String.valueOf(Play.scoresFromDB.size());

        //3.6.2 Declare current position in database
        int myPosition;

        try {
            //3.6.3 Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /*3.6.4 After the list containing current position was sorted and updated in database, the below query
                    fetches the new position according to the database scores based on the javaid generated
                    when the quiz started*/
            PreparedStatement pSt = conn.prepareStatement("select position from stats WHERE javaid=?");
            pSt.setString(1, play.getJavaid());
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                myPosition = rs.getInt("position");
                setMyRanking("You ranked " + myPosition + " out of " + totalRanks);
            }
            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
