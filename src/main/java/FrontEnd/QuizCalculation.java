package FrontEnd;

import BackEnd.BackendMain;
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

@WebServlet("/quizCalculation")
public class QuizCalculation extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        {
            try {
                String resultName = req.getParameter("resultName");
                BackendRanking insertedTrial = new BackendRanking();
                insertedTrial.setName(resultName);

                String result1 = req.getParameter("result1");
                String result2 = req.getParameter("result2");
                String result3 = req.getParameter("result3");
                String result4 = req.getParameter("result4");
                String result5 = req.getParameter("result5");

                calculateExpression(level[0], result1);
                calculateExpression(level[1], result2);
                calculateExpression(level[2], result3);
                calculateExpression(level[3], result4);
                calculateExpression(level[4], result5);


                RequestDispatcher rd = req.getRequestDispatcher("results.jsp");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //get end time
                endTime = getTime();

                //save details of quiz in database
                Database.saveInDB(resultName, insertedTrial.getDate(), score(score, nrOfLevels), quizTime(endTime, startTime), level[0], level[1], level[2], level[3], level[4]);


            } catch (ScriptException e) {
                e.printStackTrace();
            }

        }
    }
}