package Backend;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Database {

    /**
     * --------------------------------------------------------------------------------
     * 1. Declaring & initializing variables and lists
     * --------------------------------------------------------------------------------
     */

    //1.1 Declaring primitive variables
    private int score;
    private int time;
    private int position;
    private int DBid; //ID of row in DB

    //1.2 Declaring String variables
    private String name;
    private String date;
    private String javaid; //This variable is used for extracting specificic information from database
    static public String myRanking; //Position of current quiz in the ranking

    //1.3 Lists
    static public List<Database> scoresFromDB = new ArrayList<>();

    /**
     * --------------------------------------------------------------------------------
     * 2. Declaring methods & constructors
     * --------------------------------------------------------------------------------
     */

    //2.1 Default constructor
    public Database() {
    }

    //2.2 Custom constructor
    public Database(int score, int time, String name, int position, int DBid) {
        this.score = score;
        this.time = time;
        this.name = name;
        this.date = Engine.date.toString();
        this.position=position;
        this.DBid=DBid;
    }

    //2.3 Getters & Setters
    public String getJavaid() {
        return this.javaid;
    }
    public void setJavaid(String name) {this.javaid = String.valueOf(Engine.randomGenerator.nextInt(22222222)).concat(name).concat(Engine.date.toString());}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getDBid() {
        return DBid;
    }
    public void setDBid(int DBid) {
        this.DBid = DBid;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static String getMyRanking() {return myRanking;}
    public static void setMyRanking(String myRanking) {Database.myRanking = myRanking;}

    //2.4 toString override method
    @Override
    public String toString() {
        return "position= " + position +
                ", score=" + score +
                ", time=" + time +
                ", name='" + name +
                ", date=" + date;
    }

    //2.5 Saving quiz results and details in the database
    public static void saveInDB(String name, LocalDate date, double score, long seconds, String level1Expression, String level2Expression, String level3Expression, String level4Expression, String level5Expression, String javaid) {

        try {

            //2.5.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //2.5.2 Preparing the query to add the details in database
            PreparedStatement pSt = conn.prepareStatement("INSERT INTO stats (name,date,score,time,lvl1,lvl2,lvl3,lvl4,lvl5,javaid) VALUES(?,?,?,?,?,?,?,?,?,?)");
            pSt.setString(1, name);
            pSt.setDate(2, Date.valueOf(date));
            pSt.setDouble(3, score);
            pSt.setLong(4, seconds);
            pSt.setString(5, level1Expression);
            pSt.setString(6, level2Expression);
            pSt.setString(7, level3Expression);
            pSt.setString(8, level4Expression);
            pSt.setString(9, level5Expression);
            pSt.setString(10, javaid);

            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //2.6 Fetching data from database
    public static void getFromDB() {

        try {

            //2.6.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //2.6.2 Preparing the query that will extract the data
            PreparedStatement pSt = conn.prepareStatement("select name,score,time,date,dbid from stats");

            //2.6.3 Execute query
            ResultSet rs = pSt.executeQuery();

            //2.6.4 Clearing existing list as quiz results are re-ranked each time method is called
            scoresFromDB.clear();

            //2.6.5 Execute query as long as there are still items in database
            while (rs.next()) {

                //2.6.5.1 Transfer database items into a list (this is used for sorting the items from scoresFromDB list)
                Database item = new Database();
                item.setName(rs.getString("name"));
                item.setScore(rs.getInt("score"));
                item.setTime(rs.getInt("time"));
                item.setDate(rs.getString("date"));
                item.setDBid(rs.getInt("dbid"));
                scoresFromDB.add(item);
            }

            rs.close();
            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //2.7 Sorting list with database items according to score(most criterion) and time(second criterion)
    public static void sortBackendList() {

        //2.7.1 Fetch data from database
        getFromDB();

        //2.7.2 Sort items from database in scoresFromDB list according to score and time
        Comparator<Database> comparator = Comparator.comparing(Database::getScore, Comparator.reverseOrder());
        comparator = comparator.thenComparing(Database::getTime);
        scoresFromDB.sort(comparator);

        //2.7.3 Re-setting the position of the items according to the new sortation
        for (int i = 0; i < scoresFromDB.size(); i++) {
            scoresFromDB.get(i).setPosition(i);
        }
    }

    //2.8 Updating the database with the newly sorted items from sortFromDB list
    public static void updateDatabaseRanking() {

        //2.8.1 Counter used for iterating the DB update query below
        int row = 0;

        //2.8.2 Call method to sort list
        sortBackendList();


        try {

            //2.8.3 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /*2.8.4 Update new positions for each item based on the new sortation. In order to find where to introduce
                    the position for each row, DBid is used, which was previously fetched before the sortation in
                    getFromDB*/
            while (row < scoresFromDB.size()) {
                PreparedStatement pSt = conn.prepareStatement("UPDATE stats SET position=? WHERE dbid=?");
                pSt.setInt(2, scoresFromDB.get(row).getDBid());
                pSt.setInt(1, scoresFromDB.get(row).getPosition());
                row++;
                pSt.executeUpdate();
                pSt.close();
            }
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    //2.9 Calculate ranking for current quiz trial
    public static void calculateMyRank(String javaid) {

        //2.9.1 Get total number of items in database as total ranks
        String totalRanks = String.valueOf(scoresFromDB.size());

        //2.9.2 Declare current position in database
        int myPosition;

        try {
            //2.9.3 Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            /*2.9.4 After the list containing current position was sorted and update in database, the below query
                    fetches the new position according to the database scores based on the javaid generated
                    when the quiz started*/
            PreparedStatement pSt = conn.prepareStatement("select position from stats WHERE javaid=? ");
            pSt.setString(1, javaid);
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
