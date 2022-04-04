package FrontEnd;

import BackEnd.Database;

import javax.script.ScriptException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static BackEnd.Main.*;

@WebServlet("/quizServlet")
public class QuizServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        {
                try {
                    generateTwoOpLevel(1, 10, "+", "-");
                    generateTwoOpLevel(1, 15, "+", "-");
                    generateTwoOpLevel(1, 20, "+", "-");
                    generateTwoOpLevel(1, 10, "*", "-");
                    generateTwoOpLevel(1, 10, "/", "-");
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                String result1 = req.getParameter("result1");
                String result2 = req.getParameter("result2");
                String result3 = req.getParameter("result3");
                String result4 = req.getParameter("result4");
                String result5 = req.getParameter("result5");


                System.out.println(result1);
                System.out.println(result2);
                System.out.println(result3);
                System.out.println(result4);
                System.out.println(result5);


                RequestDispatcher rd=req.getRequestDispatcher("inQuiz.jsp");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            //Methods imported from Main

            //get name of player/user
//                name = getName();

            //get start time
            startTime = getTime();

            //generate 1 math level - it can be modified by using different numbers/operators

            //calculate expression result
//                calculateExpression(level1);


            //get end time
            endTime = getTime();

            //save details of quiz in database


             Database.saveInDB(name, date, score(score, nrOfLevels), quizTime(endTime, startTime), expressions[0], expressions[1], expressions[2], expressions[3], expressions[4]);


//               Database.getFromDB();
        }
    }
}