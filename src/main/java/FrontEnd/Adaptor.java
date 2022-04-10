package FrontEnd;

import Backend.Database;
import Backend.Play;
import Backend.Quiz;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Adaptor")
public class Adaptor extends HttpServlet {

    //1. Variables
    String name;
    String highestNumber;
    String numberOfExpressions;
    String resultType;
    String fixedResult;
    String resultMin;
    String resultMax;

    String errorMessage;
    List<String> userInput = new ArrayList<>();
    boolean repeatIntake = false;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        //1. Repeating intake loop until correct input is inserted

//        do {

            //1.1 Collecting user input

            //1.1.1 Clearing list before each intake*
            userInput.clear();

            //1.1.2 Fetching user input
            name = req.getParameter("name");
            highestNumber = req.getParameter("highestNumber");
            numberOfExpressions = req.getParameter("numberOfExpressions");
            resultType = req.getParameter("resultType");
            fixedResult = req.getParameter("fixedResult");
            resultMin = req.getParameter("resultMin");
            resultMax = req.getParameter("resultMax");

        do {
            //creating object based on a constructor that takes a list of items
            Quiz quiz = new Quiz(userInput);

            //taking repeat intake from quiz constructor which tells whether info is correct
            repeatIntake=quiz.isRepeatIntake();
        }
    while(repeatIntake);
    }

    }