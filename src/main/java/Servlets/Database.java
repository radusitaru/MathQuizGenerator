package Servlets;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Database")
public class Database extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println(req.getParameter("quizName"));
        getFromDB(req.getParameter("quizName"));

        //redirect to existing quiz
        RequestDispatcher rd = req.getRequestDispatcher("Results/ExistingQuizResults.jsp");
        try {
            rd.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();


        }

    }

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables & lists
     * --------------------------------------------------------------------------------
     */

    static List<String> errorList = new ArrayList<>();
    static List<String> quizNames = new ArrayList<>();
    static List<String> quizParameters = new ArrayList<>();
    public static List<Quiz> quizFromDB = new ArrayList<>();

    /**
     * --------------------------------------------------------------------------------
     * 3. Methods
     * --------------------------------------------------------------------------------
     */


    //3.2 Saving quiz results and details in the database
    public static void saveInDB(Quiz quiz) {

        try {
            //3.2.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //3.2.2 Preparing the query to add the details in database
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

    //3.3 Fetching data from database
    public static boolean checkIfQuizExists(Quiz quiz) {
        boolean quizExists = false;
        try {

            //3.3.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //3.3.2 Preparing the query that will extract the data
            PreparedStatement pSt = conn.prepareStatement("select quizname from quiz ");

            //3.3.3 Execute query
            ResultSet rs = pSt.executeQuery();

            //3.3.5 Execute query as long as there are still items in database
            while (rs.next()) {
                if (rs.getString("quizname") != null) {
                    quizNames.add(rs.getString("quizname").substring(0, quiz.getQuizName().length()));
                }
            }

            rs.close();
            pSt.close();
            ;

            if (quizNames != null) {
                for (int i = 0; i < quizNames.size(); i++) {
                    if (quizNames.get(i).equals(quiz.getQuizName())) {
                        System.out.println("QuizNameLIST :" + quizNames.get(i) + "quizNameDB: " + quiz.getQuizName());
                        quizExists = true;
                        errorList.add("Quiz already exists under this name");
                    }
                }
            }
            //3.3.2 Preparing the query that will extract the data
            PreparedStatement pSt2 = conn.prepareStatement("select parameters from quiz ");
            ResultSet rs2 = pSt2.executeQuery();
            while (rs2.next()) {
                if (rs2.getString("parameters") != null) {
                    quizParameters.add(rs2.getString("parameters").substring(0, quiz.getParameters().length()));
                }
            }
            rs2.close();
            pSt2.close();
            conn.close();
            System.out.println(quizParameters.toString());
            if (quizParameters != null) {
                for (int i = 0; i < quizParameters.size(); i++) {
                    if (quizParameters.get(i).equalsIgnoreCase(quiz.getParameters())) {
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

    public static void getFromDB(String quizName) {

        try {
            //3.3.1 Connecting to database
            final String URL = "jdbc:postgresql://localhost/";
            final String USERNAME = "postgres";
            final String PASSWORD = System.getenv("PWD");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //3.3.2 Preparing the query that will extract the data
            PreparedStatement pSt = conn.prepareStatement("select quizname, quiztype, quizid, quizdate, quizexpressionsandresults, parameters from quiz WHERE quizname=? ");
            pSt.setString(1, quizName);
            //3.3.3 Execute query
            ResultSet rs = pSt.executeQuery();
            Quiz pulledQuiz = new Quiz();
            //3.3.5 Execute query as long as there are still items in database
            while (rs.next()) {
                if (rs.getString("quizname") != null) {
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
    }
}
