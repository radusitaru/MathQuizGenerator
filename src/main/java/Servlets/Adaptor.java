package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet("/Adaptor")
public class Adaptor extends HttpServlet {

    //1. Variables

    //1.1 Primitive variables
    public static int errorCounter = 1;
    public static boolean repeatIntake = false;

    //1.2 String variables
    String quizType;
    LocalDate test = LocalDate.now();

    //1.3 Lists
    public static List<String> errorsList = new ArrayList<>();
    public static List<Integer> errorPositionInList = new ArrayList<>();
    public static List<String> randomQuizzes = new ArrayList<>();
    public static List<String> fixedResultQuizzes = new ArrayList<>();
    public static List<String> resultRangeQuizzes = new ArrayList<>();


    //1.4 Methods

    //1.4.1 Verifying user input
    public static List<String> checkInput(String userInput) {

        //1.4.2 Method variables
        int dotCounter = 0;
        int firstChar = 0;
        int nextChar = 1;
        boolean isDotFirst = false;
        boolean isDotLast = false;
        boolean isMultipleDots = false;
        boolean isStringChar = false;
        boolean isWrongInput = false;
        boolean isEmptyInput = true;
        boolean isTooLong = false;

        //1.4.3 Method lists
        List<Integer> wrongCharPosition = new ArrayList<>();
        List<String> wrongChar = new ArrayList<>();
        List<Integer> wrongDotPosition = new ArrayList<>();
        List<String> errorsList = new ArrayList<>();


        //1.4.4 Clearing errorsList
        errorsList.clear();

        //1.4.5 Method calls & objects
        Pattern lettersPattern = Pattern.compile("\\D", Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        //1.4.6 Checking if input is empty - continuing only if input is not empty
        if (!Objects.equals(userInput, "")) {
            isEmptyInput = false;


            //1.4.7 Determining whether input is non-digit & non-dot
            for (firstChar = 0; firstChar < userInput.length(); firstChar++, nextChar++) {

                //1.4.8 Initializing matcher & isStringChar boolean
                matcher = lettersPattern.matcher(userInput.substring(firstChar, nextChar));
                isStringChar = matcher.find();


                //1.4.9 If input is anything besides digits or dots, mark as wrong
                if (isStringChar && !userInput.substring(firstChar, nextChar).equals(".")) {
                    isWrongInput = true;
                    wrongChar.add(userInput.substring(firstChar, nextChar));
                    wrongCharPosition.add(firstChar + 1);
                }

                //1.4.10 Counting dots, determining whether current char is a multiple dot. If yes, save its position in String
                if (userInput.substring(firstChar, nextChar).equals(".")) {
                    dotCounter++;
                    if (dotCounter > 1) {
                        isMultipleDots = true;
                        wrongDotPosition.add(firstChar + 1);
                    }
                }
            }


            //1.4.11 Checking if dot is first or last
            if (userInput.substring(0, 1).equalsIgnoreCase(".")) isDotFirst = true;
            else if (userInput.substring(userInput.length() - 1, userInput.length()).equalsIgnoreCase("."))
                isDotLast = true;

            //1.4.12 Checking if input is too long (max 40 characters
            if (userInput.length() > 40) isTooLong = true;

        }
        //1.4.12 Determining issues
        if (isMultipleDots) {
            errorsList.add("multiple dots, on position: " + wrongDotPosition);
        }

        if (isWrongInput) {
            errorsList.add("non-digit or non-dot, on position: " + wrongCharPosition);
        }
        if (isDotFirst) {
            errorsList.add("dot first");
        }
        if (isDotLast) {
            errorsList.add("dot last");
        }
        if (isEmptyInput) {
            errorsList.add("empty input");
        }
        if (isTooLong) {
            errorsList.add("too long");
        }
        if (!isMultipleDots && !isWrongInput && !isDotFirst && !isDotLast && !isEmptyInput) {
            errorsList.add("good input");
        }

        return errorsList;
    }

    static public void resetErrorList() {
        errorsList.clear();
        errorCounter = 1;
    }

    static public void getErrorPosition() {
        for (int i = 0; i < errorsList.size(); i++) {
            errorPositionInList.add(errorsList.get(i).length());
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        resetErrorList();

        //3. Fetching quiz type
        quizType = req.getParameter("quizType");


        //4. Determining quiz type
        switch (quizType) {


            //4.1 Verifying random quiz input
            case "randomQuiz":

                resetErrorList();

                //4.1.1 Verifying quiz name input - if is empty or too long, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("quizName")).size(); i++) {
                    if (checkInput(req.getParameter("quizName")).get(i).equals("empty input")) {
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("quizName")).get(i).equals("too long")){
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }
                }

                //4.1.2 Verifying highest number input - if is too long, empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("highestNumber")).size(); i++) {
                    if (!checkInput(req.getParameter("highestNumber")).get(i).equals("good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("highestNumber")).get(i).equals("too long")){
                        errorsList.add("Error #" + errorCounter + " highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }
                }

                //4.1.3 Verifying number of expressions input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("numberOfExpressions")).size(); i++) {
                    if (!checkInput(req.getParameter("numberOfExpressions")).get(i).equals("good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("numberOfExpressions")).get(i).equals("too long")){
                        errorsList.add("Error #" + errorCounter + " number if expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }
                }

                //4.1.4 Verifying radio box for int/double
                if (req.getParameter("Integer1") == null && req.getParameter("Double1") == null) {
                    errorsList.add("Error #" + errorCounter + " on result type: " + "empty input");
                    repeatIntake = true;
                    errorCounter++;
                }


                //4.1.5 Redirect back to randomQuiz of input is invalid
                if (repeatIntake) {
                    RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/randomQuiz.jsp");
                    try {
                        rd.forward(req, resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //4.1.6 Create quiz object if user input contains no errors
                if(!repeatIntake){
                }

                break;

            //4.2 Verifying resultRangeQuiz
            case "resultRangeQuiz":

                resetErrorList();

                //4.2.1 Verifying quiz name input - if is empty or too long, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("quizName")).size(); i++) {
                    if (checkInput(req.getParameter("quizName")).get(i).equals("Empty input")) {
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("quizName")).get(i).equals("too long")){
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }
                }

                //4.2.2 Verifying highest number input - if is empty,too long or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("highestNumber")).size(); i++) {
                    if (!checkInput(req.getParameter("highestNumber")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("highestNumber")).get(i).equals("too long")){
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }
                }

                //4.2.3 Verifying number of expressions input - if is too long, empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("numberOfExpressions")).size(); i++) {
                    if (!checkInput(req.getParameter("numberOfExpressions")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("numberOfExpressions")).get(i).equals("too long")){
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }
                }

                //4.2.4 Verifying radio box for int/double
                if (req.getParameter("Integer1") == null && req.getParameter("Double1") == null) {
                    errorsList.add("Error #" + errorCounter + " on result type: " + "Empty input");
                    repeatIntake = true;
                    errorCounter++;
                }

                //4.2.5 Verifying minimum result range
                for (int i = 0; i < checkInput(req.getParameter("resultMin")).size(); i++) {
                    if (!checkInput(req.getParameter("resultMin")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on minimum range result: " + checkInput(req.getParameter("resultMin")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }

                if(checkInput(req.getParameter("resultMin")).get(i).equals("too long")) {
                    errorsList.add("Error #" + errorCounter + " on minimum range result: " + checkInput(req.getParameter("resultMin")).get(i));
                    repeatIntake = true;
                    errorCounter++;
                }
                }

                //4.2.6 Verifying maximum result range
                for (int i = 0; i < checkInput(req.getParameter("resultMax")).size(); i++) {
                    if (!checkInput(req.getParameter("resultMax")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on maximum range result: " + checkInput(req.getParameter("resultMax")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("resultMax")).get(i).equals("too long")) {
                        errorsList.add("Error #" + errorCounter + " on maximum range result: " + checkInput(req.getParameter("resultMax")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }


                    //4.2.7 Redirect back to randomQuiz if input is invalid
                    if (repeatIntake) {
                        RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/resultRangeQuiz.jsp");
                        try {
                            rd.forward(req, resp);
                        } catch (ServletException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            //4.3 Verifying fixedResultQuiz
            case "fixedResultQuiz":

                resetErrorList();

                //4.3.1 Verifying quiz name input - if is empty, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("quizName")).size(); i++) {
                    if (checkInput(req.getParameter("quizName")).get(i).equals("Empty input")) {
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("quizName")).get(i).equals("too long")){
                        i++;
                        errorsList.add("Error #" + errorCounter + " on quizName: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }

                }

                //4.3.2 Verifying highest number input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("highestNumber")).size(); i++) {
                    if (!checkInput(req.getParameter("highestNumber")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("highestNumber")).get(i).equals("too long")){
                        i++;
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }

                }

                //4.3.3 Verifying number of expressions input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("numberOfExpressions")).size(); i++) {
                    if (!checkInput(req.getParameter("numberOfExpressions")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("numberOfExpressions")).get(i).equals("too long")){
                        i++;
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;

                    }

                }

                //4.3.4 Verifying fixed result input
                for (int i = 0; i < checkInput(req.getParameter("fixedResult")).size(); i++) {
                    if (!checkInput(req.getParameter("fixedResult")).get(i).equals("fixedResult")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on fixed result: " + checkInput(req.getParameter("fixedResult")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                    if(checkInput(req.getParameter("fixedResult")).get(i).equals("too long")){
                        i++;
                        errorsList.add("Error #" + errorCounter + " fixed result: " + checkInput(req.getParameter("fixedResult")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }

                }


                //4.3.5 Redirect back to randomQuiz of input is invalid
                if (repeatIntake) {
                    RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/fixedResultQuiz.jsp");
                    try {
                        rd.forward(req, resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;

            default:
                break;
        }
        getErrorPosition();

    }

}