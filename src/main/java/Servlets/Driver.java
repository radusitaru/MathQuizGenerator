package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static Servlets.Adaptor.getErrorPosition;
import static Servlets.Adaptor.quizzes;


@WebServlet("/Driver")
public class Driver extends HttpServlet {

String command;

    /*
    Commands to write:
    1. Generate quiz with same parameters once again
    2. Go back to menu and be able to generate new quiz
    3. Be able to extract consecutive quizzes from Database

     */

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        //Get the positions of the errors
        getErrorPosition();


        switch (command)

        //Generate the quiz expressions
        Generator.generateAllExpressionsAndResults();

        //Inserting results and expressions in quiz object
        quizzes.get(0).setQuizResultsAndExpressions(Generator.allExpressionsAndResults.toString());

        //Saving quiz in database
        Database.saveInDB(quizzes.get(0));


    }
}
