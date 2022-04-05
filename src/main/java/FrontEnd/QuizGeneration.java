package FrontEnd;

import BackEnd.BackendRanking;
import BackEnd.Database;

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
        generateLevels();

        startTime = getTime();

        RequestDispatcher rd = req.getRequestDispatcher("inQuiz.jsp");

        try {
            rd.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}