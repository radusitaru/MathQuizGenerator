package FrontEnd;

import BackEnd.Database;

import javax.script.ScriptException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

import java.io.IOException;
import java.io.PrintWriter;

import static BackEnd.Main.*;

@WebServlet("/quizServlet")
public class QuizServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        {

            try {
                //get name of player/user
                name = getName();

                //get start time
                startTime = getTime();

                //generate 1 math level - it can be modified by using different numbers/operators
                String level1 = generateTwoOpLevel(1, 10, "+", "-");

                //calculate expression result
                calculateExpression(level1);

                String action = req.getParameter("answer"); // name as in the html form


                //get end time
                endTime = getTime();

                //save details of quiz in database


                Database.saveInDB(name, date, score(score, nrOfLevels), quizTime(endTime, startTime), expressions[0], expressions[1], expressions[2], expressions[3], expressions[4]);


                Database.getFromDB();
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }
}