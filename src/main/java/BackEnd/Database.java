package BackEnd;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Database extends BackendRanking {


    /**
     * --------------------------------------------------------------------------------
     * Declare & initialize global variables
     * --------------------------------------------------------------------------------
     */

    // Create list for ranking
    static public List<BackendRanking> scoresFromDB = new ArrayList<>();

    public static String getMyRanking() {
        return myRanking;
    }

    public static void setMyRanking(String myRanking) {
        Database.myRanking = myRanking;
    }

    static public String myRanking;


    /**
     * --------------------------------------------------------------------------------
     * Method for saving quiz details in the database
     * --------------------------------------------------------------------------------
     */

    public static void saveInDB(String name, LocalDate date, double score, long seconds, String level1Expression, String level2Expression, String level3Expression, String level4Expression, String level5Expression, String javaid) {
        int val = 0;

        try {
            // Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Create query
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

            // Execute
            val = pSt.executeUpdate();
            System.out.println(val);

            pSt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    /**
     * --------------------------------------------------------------------------------
     * Method for saving quiz details in the database
     * --------------------------------------------------------------------------------
     */
    public static void getFromDB() {

        try {
            // Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Create query
            PreparedStatement pSt = conn.prepareStatement("select name,score,time,date,dbid from stats");

            // Execution
            ResultSet rs = pSt.executeQuery();

            //clearing existing list as results are ranked each time method is called
            scoresFromDB.clear();

            // atata timp cat am randuri
            while (rs.next()) {


                // randul curent
                BackendRanking rank = new BackendRanking();
                rank.setName(rs.getString("name"));
                rank.setScore(rs.getInt("score"));
                rank.setTime(rs.getInt("time"));
                rank.setDate(rs.getString("date"));
                rank.setDBid(rs.getInt("dbid"));
                scoresFromDB.add(rank);
            }

            rs.close();
            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * --------------------------------------------------------------------------------
     * Method for saving sorting scores in DB
     * --------------------------------------------------------------------------------
     */

    public static void sortBackendList() {
        //get data from DB to list
        getFromDB();

        //sorting list from DB in scoresFromDB
        Comparator<BackendRanking> comparator = Comparator.comparing(BackendRanking::getScore, Comparator.reverseOrder());
        comparator = comparator.thenComparing(BackendRanking::getTime);
        scoresFromDB.sort(comparator);

        for (int i = 0; i < scoresFromDB.size(); i++) {
            scoresFromDB.get(i).setPosition(i);
        }
    }

    /**
     * --------------------------------------------------------------------------------
     * Method for updating new positions & scores into DB
     * --------------------------------------------------------------------------------
     */
    public static void UpdateDB() {

        //sort list based on position
        sortBackendList();

        //counter used for iterating the DB update query below
        int row = 0;

        try {

            // Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Insert sorted list back to DB -- the correct rows to introduce each sorted item are found based on DBid which is pulled to backend scoresFromDB from getFromDB method
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

    public static int rankingInDB(BackendRanking trial) {
        return trial.getPosition();
    }

    public static void calculateMyRank(String javaid) {
        String totalRanks = String.valueOf(scoresFromDB.size());
        int myPosition = 0;
        try {
            // Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Create query and execute
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
