package Servlets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Database")
public class Database extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
    }

    /**
     * --------------------------------------------------------------------------------
     * 1. Lists
     * --------------------------------------------------------------------------------
     */

    static List<String> errorList = new ArrayList<>();
    static List<String> quizNames = new ArrayList<>();
    static List<String> quizParameters = new ArrayList<>();
    public static List<Quiz> quizFromDB = new ArrayList<>();


    /**
     * --------------------------------------------------------------------------------
     * 2. Methods
     * --------------------------------------------------------------------------------
     */

    //2.1 Saving quiz results and details in the database
    public static void saveInDB(Quiz quiz) {

        try {
            //2.1.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //2.1.2 Preparing the query to add the details in database
            PreparedStatement pSt = conn.prepareStatement("INSERT INTO quiz (quizname, quiztype, quizid, quizdate, quizexpressionsandresults, parameters ) VALUES(?,?,?,?,?,?)");
            pSt.setString(1, quiz.getQuizName());
            pSt.setString(2, quiz.getQuizType());
            pSt.setString(3, quiz.getQuizId());
            pSt.setString(4, quiz.getQuizDate());
            pSt.setString(5, quiz.getQuizResultsAndExpressions());
            pSt.setString(6, quiz.getParameters());

            pSt.executeUpdate();
            pSt.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //2.2 Fetching data from database
    public static boolean checkIfQuizExists(String quizName) {
        boolean quizExists = false;
        try {

            //2.2.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //2.2.2 Preparing the query that will extract the data
            PreparedStatement pSt = conn.prepareStatement("select quizname from quiz ");

            //2.2.3 Execute query
            ResultSet rs = pSt.executeQuery();

            //2.2.4 Execute query as long as there are still items in database
            while (rs.next()) {
                if (rs.getString("quizname") != null) {
                    quizNames.add(rs.getString("quizname").substring(0, quizName.length()));
                }
            }
            rs.close();
            pSt.close();

            //2.2.5 If quizName input is not null, check if quiz exists in DB based on quizName
            if (quizNames != null) {
                for (int i = 0; i < quizNames.size(); i++) {
                    if (quizNames.get(i).equalsIgnoreCase(quizName)) {
                        System.out.println("QuizNameLIST :" + quizNames.get(i) + "quizNameDB: " + quizName);
                        quizExists = true;
                        errorList.add("Quiz already exists under this name");
                    }
                }
            }

            //2.2.6 Preparing the query that will extract the data
            PreparedStatement pSt2 = conn.prepareStatement("select parameters from quiz ");
            ResultSet rs2 = pSt2.executeQuery();
            while (rs2.next()) {
                if (rs2.getString("parameters") != null) {
                    quizParameters.add(rs2.getString("parameters").substring(0, quizName.length()));
                }
            }
            rs2.close();
            pSt2.close();
            conn.close();

            //2.2.7 If quizParameters input is not null, check if quiz exists in DB based on quizParameters
            if (quizParameters != null) {
                for (int i = 0; i < quizParameters.size(); i++) {
                    if (quizParameters.get(i).equalsIgnoreCase(quizName)) {
                        quizExists = true;
                        errorList.add("Quiz already exists under these parameters");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return quizExists;
    }

    //2.3 Get quiz from database
    public static boolean getFromDB(String quizName) {
        boolean isInDB = false;
        try {

            //2.3.1 Clearing quizFromDB list
            quizFromDB.clear();

            //2.3.2 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //2.3.3 Preparing the query that will extract the data based on quizName
            PreparedStatement pSt = conn.prepareStatement("select quizname, quiztype, quizid, quizdate, quizexpressionsandresults, parameters from quiz WHERE quizname=? ");
            pSt.setString(1, quizName);

            //2.3.4 Execute query
            ResultSet rs = pSt.executeQuery();

            //2.3.5 Create quiz object in which details from DB will be introduced
            Quiz pulledQuiz = new Quiz();

            //2.3.6 Execute query as long as there are still items in database: introduce results in quiz object
            while (rs.next()) {
                if (rs.getString("quizname") != null) {
                    isInDB = true;
                    pulledQuiz.setQuizName((rs.getString("quizname")));
                    pulledQuiz.setQuizType((rs.getString("quiztype")));
                    pulledQuiz.setQuizId((rs.getString("quizid")));
                    pulledQuiz.setQuizDate((rs.getString("quizdate")));
                    pulledQuiz.setQuizResultsAndExpressions((rs.getString("quizexpressionsandresults")));
                    pulledQuiz.setParameters((rs.getString("parameters")));
                    quizFromDB.add(pulledQuiz);
                }
            }
            rs.close();
            pSt.close();
            conn.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isInDB;
    }
}
