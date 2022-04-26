package Servlets;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DataCollection")
public class DataCollection extends HttpServlet {

    public static List<String> commands = new ArrayList<>();
    public static List<String> quizInput = new ArrayList<>();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        /**
         * --------------------------------------------------------------------------------
         * 1. Collecting data
         * --------------------------------------------------------------------------------
         */

        //1.1 Clearing list of commands
        commands.clear();

        //1.2 If command is generate quiz
        if (req.getParameter("command").equals("generateQuiz")) {

            //1.2.1 Clear quiz input list to make space for collecting new data
            quizInput.clear();

            //1.2.2 Save command for sending it later on to Driver servlet
            commands.add(req.getParameter("command"));

            //1.2.3 Saving quiz input as per quiz type
            switch (req.getParameter("quizType")) {
                case "randomQuiz":
                    quizInput.add(req.getParameter("quizType"));
                    quizInput.add(req.getParameter("quizName"));
                    quizInput.add(req.getParameter("highestNumber"));
                    quizInput.add(req.getParameter("numbersInExpression"));
                    quizInput.add(req.getParameter("numberOfExpressions"));
                    quizInput.add(req.getParameter("Integer1"));
                    quizInput.add(req.getParameter("Double1"));
                    quizInput.add(req.getParameter("add"));
                    quizInput.add(req.getParameter("subtract"));
                    quizInput.add(req.getParameter("multiply"));
                    quizInput.add(req.getParameter("divide"));
                    break;
                case "resultRangeQuiz":
                    quizInput.add(req.getParameter("quizType"));
                    quizInput.add(req.getParameter("quizName"));
                    quizInput.add(req.getParameter("highestNumber"));
                    quizInput.add(req.getParameter("numbersInExpression"));
                    quizInput.add(req.getParameter("numberOfExpressions"));
                    quizInput.add(req.getParameter("Integer1"));
                    quizInput.add(req.getParameter("Double1"));
                    quizInput.add(req.getParameter("resultMin"));
                    quizInput.add(req.getParameter("resultMax"));
                    quizInput.add(req.getParameter("add"));
                    quizInput.add(req.getParameter("subtract"));
                    quizInput.add(req.getParameter("multiply"));
                    quizInput.add(req.getParameter("divide"));
                    break;
                case "fixedResultQuiz":
                    quizInput.add(req.getParameter("quizType"));
                    quizInput.add(req.getParameter("quizName"));
                    quizInput.add(req.getParameter("highestNumber"));
                    quizInput.add(req.getParameter("numbersInExpression"));
                    quizInput.add(req.getParameter("numberOfExpressions"));
                    quizInput.add(req.getParameter("fixedResult"));
                    quizInput.add(req.getParameter("add"));
                    quizInput.add(req.getParameter("subtract"));
                    quizInput.add(req.getParameter("multiply"));
                    quizInput.add(req.getParameter("divide"));
                    break;
            }
            //1.2.4 Redirecting to error handling servlet
            RequestDispatcher rd = req.getRequestDispatcher("/ErrorHandling");
            try {
                rd.forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //1.3 Catching generate again command
        } else if (req.getParameter("command").equals("generateAgain")) {

            //1.3.1 Saving command to send to Driver servlet
            commands.add(req.getParameter("command"));

            //1.3.2 Redirect to Driver servlet
            RequestDispatcher rd = req.getRequestDispatcher("/Driver");
            try {
                rd.forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //1.4 Catching other commands
        } else if (!req.getParameter("command").equals("generateQuiz") &&
                !req.getParameter("command").equals("generateAgain") &&
                req.getParameter("command") != null) {

            //1.4.1 Saving other commands
            commands.add(req.getParameter("command"));

            //1.4.2 Redirect to Driver servlet
            RequestDispatcher rd = req.getRequestDispatcher("/Driver");
            try {
                rd.forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}