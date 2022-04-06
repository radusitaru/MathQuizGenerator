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


@WebServlet("/quizCalculation")
public class QuizCalculation extends HttpServlet {

    public static BackendRanking insertedTrial = new BackendRanking();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        {
            try {
                String resultName = req.getParameter("resultName");
                insertedTrial.setName(resultName);
                insertedTrial.setJavaid(resultName);
                System.out.println("Javaid"+insertedTrial.getJavaid());

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




                //get end time
                endTime = getTime();

                //calculate quiz time

                myQuizTime= (int) quizTime(startTime,endTime);

                //save details of quiz in database
                Database.saveInDB(resultName, date, score(score, nrOfLevels), quizTime(endTime, startTime), level[0], level[1], level[2], level[3], level[4], insertedTrial.getJavaid());

                Database.getFromDB();
                Database.UpdateDB();
                Database.calculateMyRank(QuizCalculation.insertedTrial.getJavaid());

                RequestDispatcher rd = req.getRequestDispatcher("results.jsp");
                try {
                    rd.forward(req, resp);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }

            } catch (ScriptException e) {
                e.printStackTrace();
            }

        }
    }
}