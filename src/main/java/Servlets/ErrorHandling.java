package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet("/ErrorHandling")
public class ErrorHandling extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {


        //Check and save errors
        checkAndSaveErrors();

        //If user input is incorrect, repeat intake
        if (incorrectInput) {
            RequestDispatcher rd = req.getRequestDispatcher("QuizTypes/" + DataCollection.quizInput.get(0) + ".jsp");
            try {
                rd.forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            RequestDispatcher rd2 = req.getRequestDispatcher("/Driver");
            try {
                rd2.forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables
     * --------------------------------------------------------------------------------
     */


    //1.1 Primitive variables
    public static int errorCounter = 1;
    public static boolean incorrectInput = false;
    public static boolean isDotFirst = false;
    public static boolean isDotLast = false;
    public static boolean isMultipleDots = false;
    public static boolean isStringChar;
    public static boolean isWrongInput = false;
    public static boolean isEmptyInput = true;
    public static boolean isTooLong = false;
    public static boolean isDouble = false;
    public static boolean isEmptyName = false;

    //1.3 Lists
    public static List<String> errorsList = new ArrayList<>();
    public static List<String> inputOperators = new ArrayList<>();

    /**
     * --------------------------------------------------------------------------------
     * 2. Methods
     * --------------------------------------------------------------------------------
     */

    //2.1 Check number input
    public static void checkNumbers(String userInput) {

        //2.1.1  Method variables
        int dotCounter = 0;
        int firstChar;
        int nextChar = 1;

        //2.1.2 Method calls & objects
        Pattern lettersPattern = Pattern.compile("\\D", Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        //2.1.3 Checking if input is empty - continuing only if input is not empty
        if (!Objects.equals(userInput, "")) {
            isEmptyInput = false;


            //2.1.4 Determining whether input is non-digit & non-dot
            for (firstChar = 0; firstChar < userInput.length(); firstChar++, nextChar++) {

                //2.1.5 Initializing matcher & isStringChar boolean
                matcher = lettersPattern.matcher(userInput.substring(firstChar, nextChar));
                isStringChar = matcher.find();


                //2.1.6 If input is anything besides digits or dots, mark as wrong.
                if (isStringChar && !userInput.substring(firstChar, nextChar).equals(".")) {
                    isWrongInput = true;
                }

                //2.1.7 Counting dots, determining whether current char is a multiple dot. If yes, save its position in String
                if (userInput.substring(firstChar, nextChar).equals(".")) {
                    dotCounter++;
                    if (dotCounter > 1) {
                        isMultipleDots = true;
                    }
                }
            }

            //2.1.8 Checking if dot is first or last
            if (userInput.substring(0, 1).equalsIgnoreCase(".")) isDotFirst = true;
            else if (userInput.substring(userInput.length() - 1, userInput.length()).equalsIgnoreCase("."))
                isDotLast = true;

            //2.1.9 Checking if input is too long (max 40 characters)
            if (userInput.length() > 40) isTooLong = true;
        } else isEmptyInput = true;
    }

    //2.2 Chneck result type
    public static void checkResultType(String intResult, String doubleResult) {

        //2.2.1 Verifying check box for int/double
        if (intResult == null && doubleResult == null) {
            isEmptyInput = true;

        }
        if (intResult != null && doubleResult != null) {
            isDouble = true;

        }

    }

    //2.3 Check operators
    public static void checkOperators(String add, String subtract, String multiply, String divide) {

        //2.3.1 Verifying if input is an operator, or if input is empty
        if (add == null
                && subtract == null
                && multiply == null
                && divide == null) {
            isEmptyInput = true;

        }

        //2.3.2 Collecting operators input
        if (!incorrectInput) {
            if (add != null) inputOperators.add("+");
            if (subtract != null) inputOperators.add("-");
            if (multiply != null) inputOperators.add("*");
            if (divide != null) inputOperators.add("/");
        }
    }

    //2.4 Check quiz name
    public static void checkQuizName(String quizNameInput) {
        if (quizNameInput==null) {
            isEmptyName = true;
        }
    }

    //2.5 Check and save errors
    public static void checkAndSaveErrors() {

        //2.5.1 clearing error list
        resetErrorList();

        //2.5.2 Running all checks for each quiz type
        switch (DataCollection.quizInput.get(0)) {
            case "randomQuiz":
                checkQuizName(DataCollection.quizInput.get(1));
                saveErrors("quiz name");
                checkNumbers(DataCollection.quizInput.get(2));
                saveErrors("highest number");
                checkNumbers(DataCollection.quizInput.get(3));
                saveErrors("numbers in expression");
                checkNumbers(DataCollection.quizInput.get(4));
                saveErrors("number of expressions");
                checkResultType(DataCollection.quizInput.get(5), DataCollection.quizInput.get(6));
                saveErrors("result type");
                checkOperators(DataCollection.quizInput.get(7), DataCollection.quizInput.get(8), DataCollection.quizInput.get(9), DataCollection.quizInput.get(10));
                saveErrors("operators");
                break;
            case "resultRangeQuiz":
                checkQuizName(DataCollection.quizInput.get(1));
                saveErrors("quiz name");
                checkNumbers(DataCollection.quizInput.get(2));
                saveErrors("highest number");
                checkNumbers(DataCollection.quizInput.get(3));
                saveErrors("numbers in expression");
                checkNumbers(DataCollection.quizInput.get(4));
                saveErrors("number of expressions");
                checkResultType(DataCollection.quizInput.get(5), DataCollection.quizInput.get(6));
                saveErrors("result type");
                checkNumbers(DataCollection.quizInput.get(7));
                saveErrors("result minimum");
                checkNumbers(DataCollection.quizInput.get(8));
                saveErrors("result maximum");
                checkOperators(DataCollection.quizInput.get(9), DataCollection.quizInput.get(10), DataCollection.quizInput.get(11), DataCollection.quizInput.get(12));
                saveErrors("operators");
                break;
            case "fixedResultQuiz":
                checkQuizName(DataCollection.quizInput.get(1));
                saveErrors("quiz name");
                checkNumbers(DataCollection.quizInput.get(2));
                saveErrors("highest number");
                checkNumbers(DataCollection.quizInput.get(3));
                saveErrors("numbers in expression");
                checkNumbers(DataCollection.quizInput.get(4));
                saveErrors("number of expressions");
                checkNumbers(DataCollection.quizInput.get(5));
                saveErrors("fixed result");
                checkOperators(DataCollection.quizInput.get(6), DataCollection.quizInput.get(7), DataCollection.quizInput.get(8), DataCollection.quizInput.get(9));
                saveErrors("operators");
        }
    }

    //2.6 Save errors in errors list
    public static void saveErrors(String parameter) {

        if (isDotFirst) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "dot first");
            errorCounter++;
            incorrectInput = true;
            isDotFirst = false;
        }
        if (isDotLast) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "dot last");
            errorCounter++;
            incorrectInput = true;
            isDotLast = false;
        }
        if (isMultipleDots) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "multiple dots");
            errorCounter++;
            incorrectInput = true;
            isMultipleDots = false;
        }
        if (isWrongInput) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "non digit");
            errorCounter++;
            incorrectInput = true;
            isWrongInput = false;
        }
        if (isEmptyInput) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "empty input");
            errorCounter++;
            incorrectInput = true;
            isEmptyInput = false;
        }
        if (isEmptyName) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "empty quizName");
            errorCounter++;
            incorrectInput = true;
            isEmptyName = false;
        }
        if (isTooLong) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "input is too long");
            errorCounter++;
            incorrectInput = true;
            isTooLong = false;
        }
        if (isDouble) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> " + parameter + ": " + "multiple selection");
            errorCounter++;
            incorrectInput = true;
            isDouble = false;
        }
    }
    //2.7 Reset errors list
    static public void resetErrorList() {
        errorsList.clear();
        errorCounter = 1;
    }
}
