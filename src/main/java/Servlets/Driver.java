package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static Servlets.Database.getFromDB;


@WebServlet("/Driver")
public class Driver extends HttpServlet {

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables
     * --------------------------------------------------------------------------------
     */

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        /**
         * --------------------------------------------------------------------------------
         * 2. Driver (managing commands from user input)
         * --------------------------------------------------------------------------------
         */


        //2.1 Catch generateQuiz & generateAgain commands
        if (DataCollection.commands.get(0).equals("generateQuiz") || DataCollection.commands.get(0).equals("generateAgain")) {

            //2.1.2 generateQuiz command
            switch (DataCollection.commands.get(0)) {
                case "generateQuiz":
                    Quiz.generateQuiz();

                    RequestDispatcher rd1 = req.getRequestDispatcher("/Results/Results.jsp");
                    try {
                        rd1.forward(req, resp);
                    } catch (ServletException | IOException e) {
                        e.printStackTrace();
                    }
                    break;

                //2.1.3 generateAgain command
                case "generateAgain":

                    //2.1.4 Clearing lists so that details from previous generation are deleted
                    Quiz.resetQuizzesList();
                    Generator.clearAllGeneratorLists();
                    Quiz.generateQuiz();

                    RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                    try {
                        rd6.forward(req, resp);
                    } catch (ServletException | IOException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
        //2.2 Catching rest of commands
        else {
            switch (DataCollection.commands.get(0)) {
                //2.2.1 Save quiz in database
                case "saveQuizInDB":

                    Database.saveInDB(Quiz.quizzes.get(0));
                    //2.2.1.1
                    RequestDispatcher rd = req.getRequestDispatcher("/Results/SavedQuiz.jsp");
                    try {
                        rd.forward(req, resp);
                    } catch (ServletException | IOException e) {
                        e.printStackTrace();
                    }

                    break;

                //2.2.2 Back to menu
                case "backToMenu":

                    //2.2.2.1 Reset quiz & expression lists
                    Quiz.resetQuizzesList();
                    Generator.clearAllGeneratorLists();

                    //2.2.2.2 Redirect back to Menu
                    RequestDispatcher rd7 = req.getRequestDispatcher("/Navigation/Menu.html");
                    try {
                        rd7.forward(req, resp);
                    } catch (ServletException | IOException e) {
                        e.printStackTrace();
                    }
                    break;

                //2.2.3 Get results quiz from database:
                case "dbQuizName":

                    //2.2.3.1 Pulling quiz fromDB
                    if (getFromDB(req.getParameter("quizName"))) {

                        //2.2.3.2 Redirect to results page once commands are completed and
                        RequestDispatcher rd9 = req.getRequestDispatcher("/Results/ExistingQuizResults.jsp");
                        try {
                            rd9.forward(req, resp);
                        } catch (ServletException | IOException e) {
                            e.printStackTrace();
                        }
                    } else ErrorHandling.errorsList.add("<b>Error #1: No such quiz in database, try again");
                    RequestDispatcher rd4 = req.getRequestDispatcher("/Navigation/ExistingQuiz.jsp");
                    try {
                        rd4.forward(req, resp);
                    } catch (ServletException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}