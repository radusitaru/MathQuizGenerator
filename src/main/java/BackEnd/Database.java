package BackEnd;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Database extends Ranking {



    /**
     * --------------------------------------------------------------------------------
     * Declare & initialize global variables
     * --------------------------------------------------------------------------------
     */

    static private int maxScoreDB;

    // Create list for ranking
    static public List<Ranking> scoresFromDB = new ArrayList<>();


    /**
     * --------------------------------------------------------------------------------
     * Method for saving quiz details in the database
     * --------------------------------------------------------------------------------
     */

    public static void saveInDB(String name, LocalDate date, double score, long seconds, String level1Expression, String level2Expression, String level3Expression, String level4Expression, String level5Expression) {
        int val = 0;

        try {
            // Connect to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Create query
            PreparedStatement pSt = conn.prepareStatement("INSERT INTO stats (name,date,score,time,lvl1,lvl2,lvl3,lvl4,lvl5) VALUES(?,?,?,?,?,?,?,?,?)");
            pSt.setString(1, name);
            pSt.setDate(2, Date.valueOf(date));
            pSt.setDouble(3, score);
            pSt.setLong(4, seconds);
            pSt.setString(5, level1Expression);
            pSt.setString(6, level2Expression);
            pSt.setString(7, level3Expression);
            pSt.setString(8, level4Expression);
            pSt.setString(9, level5Expression);

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
            PreparedStatement pSt = conn.prepareStatement("select name,score,time,date from stats");

            // Execution
            ResultSet rs = pSt.executeQuery();

            //clearing existing list as results are ranked each time method is called
            scoresFromDB.clear();

            // atata timp cat am randuri
            while (rs.next()) {


                // randul curent
                Ranking rank = new Ranking();
                rank.setName(rs.getString("name"));
                rank.setScore(rs.getInt("score"));
                rank.setTime(rs.getInt("time"));
                rank.setDate(rs.getDate("date"));
                scoresFromDB.add(rank);
            }

            //sorting list
            Comparator<Ranking> comparator = Comparator.comparing(Ranking::getScore,Comparator.reverseOrder());
            comparator = comparator.thenComparing(Ranking::getTime);
            scoresFromDB.sort(comparator);

            for (Ranking ranking : scoresFromDB) {
                System.out.println(ranking);
            }
            rs.close();
            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

