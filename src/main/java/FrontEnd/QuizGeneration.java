package FrontEnd;

import BackEnd.BackendRanking;
import BackEnd.Database;

import javax.script.ScriptException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static BackEnd.BackendMain.*;

@WebServlet("/quizGeneration")
public class QuizGeneration extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

       int difficulty = Integer.parseInt(req.getParameter("difficulty"));


        try {
            Expression[0] = TwoOperatorExpression(difficulty, "+", "-");
            Expression[1] = TwoOperatorExpression(difficulty, "+", "-");
            Expression[2] = TwoOperatorExpression(difficulty, "+", "-");
            Expression[3] = TwoOperatorExpression(difficulty, "+", "-");
            Expression[4] = TwoOperatorExpression(difficulty, "+", "-");

        } catch (ScriptException e) {
            e.printStackTrace();
        }

        startTime = getTime();

        RequestDispatcher rd = req.getRequestDispatcher("inQuiz.jsp");

        try {
            rd.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}