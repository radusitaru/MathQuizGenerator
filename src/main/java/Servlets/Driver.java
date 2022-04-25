package Servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Servlets.Database.getFromDB;


@WebServlet("/Driver")
public class Driver extends HttpServlet {

    //1. Variables

    //1.1 Primitive variables
    public static int errorCounter = 1;
    public static boolean repeatIntake = false;

    //1.2 String variables
    String quizType;

    //1.3 Lists
    public static List<String> errorsList = new ArrayList<>();
    public static List<Integer> errorPositionInList = new ArrayList<>();
    public static List<Quiz> quizzes = new ArrayList<>();
    public static List<String> inputOperators = new ArrayList<>();
    public static List<String> quizInput = new ArrayList<>();


    //1.4 Methods

    //1.4.1 Verifying user input
    public static List<String> checkInput(String userInput) {

        //1.4.2 Method variables
        int dotCounter = 0;
        int firstChar;
        int nextChar = 1;
        boolean isDotFirst = false;
        boolean isDotLast = false;
        boolean isMultipleDots = false;
        boolean isStringChar;
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

    static public void resetQuizzesList() {
        quizzes.clear();
    }

    static public void getErrorPosition() {
        for (int i = 0; i < errorsList.size(); i++) {
            errorPositionInList.add(errorsList.get(i).length());
        }
    }

    static public boolean checkInputAndGenerateRandomQuiz(String quizName,
                                                          String highestNumber,
                                                          String numbersInExpression,
                                                          String numberOfExpressions,
                                                          String intResult,
                                                          String doubleResult,
                                                          String add,
                                                          String subtract,
                                                          String multiply,
                                                          String divide) {
        repeatIntake = false;
        resetErrorList();
        String resultType;

        //Collecting result type
        if (intResult == null) resultType = doubleResult;
        else resultType = intResult;

        //4.1.1 Verifying quiz name input - if is empty or too long, repeat intake and save error message
        for (int i = 0; i < checkInput(quizName).size(); i++) {
            if (checkInput(quizName).get(i).equals("empty input")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on quiz name: " + checkInput(quizName).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(quizName).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on quiz name: " + checkInput(quizName).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.1.2 Verifying highest number input - if is too long, empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(highestNumber).size(); i++) {
            if (!checkInput(highestNumber).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on highest number: " + checkInput(highestNumber).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(highestNumber).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> highest number: " + checkInput(highestNumber).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.1.3 Verifying numbers in expression input - if is empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(numbersInExpression).size(); i++) {
            if (!checkInput(numbersInExpression).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on numbers in expression: " + checkInput(numbersInExpression).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(numbersInExpression).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> numbers in expression: " + checkInput(numbersInExpression).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.1.4 Verifying number of expressions input - if is empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(numberOfExpressions).size(); i++) {
            if (!checkInput(numberOfExpressions).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on number of expressions: " + checkInput(numberOfExpressions).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(numberOfExpressions).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> number of expressions: " + checkInput(numberOfExpressions).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.1.5 Verifying radio box for int/double
        if (intResult == null && doubleResult == null) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> on result type: " + "empty input");
            repeatIntake = true;
            errorCounter++;
        }

        //4.1.6 Verifying operators input
        if (add == null
                && subtract == null
                && multiply == null
                && divide == null) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> on operators: " + "empty input");
            repeatIntake = true;
            errorCounter++;
            System.out.println("operators repeatIntake=" + repeatIntake);
        }

        //4.1.7 Collecting operators input
        if (!repeatIntake) {
            if (add != null) inputOperators.add("+");
            if (subtract != null) inputOperators.add("-");
            if (multiply != null) inputOperators.add("*");
            if (divide != null) inputOperators.add("/");
        }

        //4.1.8 Create quiz object if user input contains no errors
        if (!repeatIntake) {
            resetQuizzesList();
            Quiz randomQuiz = new Quiz(quizName,
                    inputOperators,
                    Integer.parseInt(highestNumber),
                    Integer.parseInt(numbersInExpression),
                    Integer.parseInt(numberOfExpressions),
                    resultType);
            quizzes.add(randomQuiz);
            Generator.generateAllExpressions();
            Generator.concatenateExpressionsAndResults();
            randomQuiz.setQuizResultsAndExpressions();
        }

        //4.1.9 Check if quiz exists
        if (!repeatIntake) {
            if (Database.checkIfQuizExists(quizzes.get(0))) {
                repeatIntake = true;
                errorsList.add(Database.errorList.toString());
                quizzes.clear();
            }
        }

        //4.1.10 If input is correct, save input in list
        if (!repeatIntake) {
            quizInput.clear();
            quizInput.add(quizName);
            quizInput.add(highestNumber);
            quizInput.add(numbersInExpression);
            quizInput.add(numberOfExpressions);
            quizInput.add(intResult);
            quizInput.add(doubleResult);
            quizInput.add(add);
            quizInput.add(subtract);
            quizInput.add(multiply);
            quizInput.add(divide);
        }

        return repeatIntake;
    }

    static public boolean checkInputAndGenerateResultRangeQuiz(String quizName,
                                                               String highestNumber,
                                                               String numbersInExpression,
                                                               String numberOfExpressions,
                                                               String intResult,
                                                               String doubleResult,
                                                               String resultMin,
                                                               String resultMax,
                                                               String add,
                                                               String subtract,
                                                               String multiply,
                                                               String divide) {

        repeatIntake = false;
        resetErrorList();
        String resultType;

        //Collecting result type
        if (intResult == null) resultType = doubleResult;
        else resultType = intResult;

        //4.2.1 Verifying quiz name input - if is empty or too long, repeat intake and save error message
        for (int i = 0; i < checkInput(quizName).size(); i++) {
            if (checkInput(quizName).get(i).equals("Empty input")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on quiz name: " + checkInput(quizName).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(quizName).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on quiz name: " + checkInput(quizName).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.2.2 Verifying highest number input - if is empty,too long or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(highestNumber).size(); i++) {
            if (checkInput(highestNumber).get(i).equals("empty input")
                    ||
                    checkInput(highestNumber).get(i).equals("empty input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on highest number: " + checkInput(highestNumber).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(highestNumber).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on highest number: " + checkInput(highestNumber).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.2.3 Verifying numbers in expression input - if is empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(numbersInExpression).size(); i++) {
            if (!checkInput(numbersInExpression).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on numbers in expression: " + checkInput(numbersInExpression).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(numbersInExpression).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> numbers in expression: " + checkInput(numbersInExpression).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.2.4 Verifying number of expressions input - if is too long, empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(numberOfExpressions).size(); i++) {
            if (!checkInput(numberOfExpressions).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on number of expressions: " + checkInput(numberOfExpressions).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(numberOfExpressions).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on number of expressions: " + checkInput(numberOfExpressions).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.2.5 Verifying radio box for int/double
        if (intResult == null && doubleResult == null) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> on result type: " + "empty input");
            repeatIntake = true;
            errorCounter++;
        }

        //4.2.6 Verifying minimum result range
        for (int i = 0; i < checkInput(resultMin).size(); i++) {
            if (!checkInput(resultMin).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on minimum range result: " + checkInput(resultMin).get(i));
                repeatIntake = true;
                errorCounter++;
            }

            if (checkInput(resultMin).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on minimum range result: " + checkInput(resultMin).get(i));
                repeatIntake = true;
                errorCounter++;
            }
        }

        //4.2.7 Verifying maximum result range
        for (int i = 0; i < checkInput(resultMax).size(); i++) {
            if (!checkInput(resultMax).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on maximum range result: " + checkInput(resultMax).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(resultMax).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on maximum range result: " + checkInput(resultMax).get(i));
                repeatIntake = true;
                errorCounter++;

            }

            //4.2.8 Verifying if input is an operator, or if input is empty
            if (add == null
                    && subtract == null
                    && multiply == null
                    && divide == null) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on operators: " + "empty input");
                repeatIntake = true;
                errorCounter++;
            }

            //4.2.9 Collecting operators input
            if (!repeatIntake) {
                if (add != null) inputOperators.add("+");
                if (subtract != null) inputOperators.add("-");
                if (multiply != null) inputOperators.add("*");
                if (divide != null) inputOperators.add("/");
            }

            //4.2.10 Create quiz object if user input contains no errors
            if (!repeatIntake) {
                Quiz resultRangeQuiz = new Quiz(quizName,
                        inputOperators,
                        Integer.parseInt(highestNumber),
                        Integer.parseInt(numbersInExpression),
                        Integer.parseInt(numberOfExpressions),
                        resultType,
                        Double.parseDouble(resultMin),
                        Double.parseDouble(resultMax));
                quizzes.add(resultRangeQuiz);
                Generator.generateAllExpressions();
                Generator.concatenateExpressionsAndResults();
                resultRangeQuiz.setQuizResultsAndExpressions();
            }

            //4.2.11 Check if quiz exists
            if (!repeatIntake) {
                if (Database.checkIfQuizExists(quizzes.get(0))) {
                    repeatIntake = true;
                    errorsList.add(Database.errorList.toString());
                    quizzes.clear();
                }
            }
        }

        //4.2.12 If input is correct, save input in list
        if (!repeatIntake) {
            quizInput.clear();
            quizInput.add(quizName);
            quizInput.add(highestNumber);
            quizInput.add(numbersInExpression);
            quizInput.add(numberOfExpressions);
            quizInput.add(intResult);
            quizInput.add(doubleResult);
            quizInput.add(resultMin);
            quizInput.add(resultMax);
            quizInput.add(add);
            quizInput.add(subtract);
            quizInput.add(multiply);
            quizInput.add(divide);
        }

        return repeatIntake;
    }

    public static boolean checkInputAndGenerateFixedResultQuiz(String quizName,
                                                               String highestNumber,
                                                               String numbersInExpression,
                                                               String numberOfExpressions,
                                                               String fixedResult,
                                                               String add,
                                                               String subtract,
                                                               String multiply,
                                                               String divide) {

        //4.3.1 Verifying quiz name input - if is empty, repeat intake and save error message
        for (int i = 0; i < checkInput(quizName).size(); i++) {
            if (checkInput(quizName).get(i).equals("empty input")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on quiz name: " + checkInput(quizName).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(quizName).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on quizName: " + checkInput(quizName).get(i));
                repeatIntake = true;
                errorCounter++;
            }

        }

        //4.3.2 Verifying highest number input - if is empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(highestNumber).size(); i++) {
            if (!checkInput(highestNumber).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on highest number: " + checkInput(highestNumber).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(highestNumber).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on highest number: " + checkInput(highestNumber).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.3.3 Verifying numbers in expression input - if is empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(numbersInExpression).size(); i++) {
            if (!checkInput(numbersInExpression).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on numbers in expression: " + checkInput(numbersInExpression).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(numbersInExpression).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> numbers in expression: " + checkInput(numbersInExpression).get(i));
                repeatIntake = true;
                errorCounter++;

            }
        }

        //4.3.4 Verifying number of expressions input - if is empty or non-digit, repeat intake and save error message
        for (int i = 0; i < checkInput(numberOfExpressions).size(); i++) {
            if (!checkInput(numberOfExpressions).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on number of expressions: " + checkInput(numberOfExpressions).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(numberOfExpressions).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on number of expressions: " + checkInput(numberOfExpressions).get(i));
                repeatIntake = true;
                errorCounter++;

            }

        }

        //4.3.5 Verifying fixed result input
        for (int i = 0; i < checkInput(fixedResult).size(); i++) {
            if (!checkInput(fixedResult).get(i).equals("good input")
            ) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> on fixed result: " + checkInput(fixedResult).get(i));
                repeatIntake = true;
                errorCounter++;
            }
            if (checkInput(fixedResult).get(i).equals("too long")) {
                errorsList.add("<br> <b>Error #" + errorCounter + "</b> fixed result: " + checkInput(fixedResult).get(i));
                repeatIntake = true;
                errorCounter++;
            }

        }
        //4.3.6 Collecting operators input
        if (!repeatIntake) {
            if (add != null) inputOperators.add("+");
            if (subtract != null) inputOperators.add("-");
            if (multiply != null) inputOperators.add("*");
            if (divide != null) inputOperators.add("/");
        }

        //4.3.8 Create quiz object if user input contains no errors
        if (!repeatIntake) {
            Quiz fixedResultQuiz = new Quiz(quizName,
                    inputOperators,
                    Integer.parseInt(highestNumber),
                    Integer.parseInt(numbersInExpression),
                    Integer.parseInt(numberOfExpressions),
                    Double.parseDouble(fixedResult));
            quizzes.add(fixedResultQuiz);
            Generator.generateAllExpressions();
            Generator.concatenateExpressionsAndResults();
            fixedResultQuiz.setQuizResultsAndExpressions();
        }


        //4.1.7 Check if quiz exists
        if (!repeatIntake) {
            if (Database.checkIfQuizExists(quizzes.get(0))) {
                repeatIntake = true;
                errorsList.add(Database.errorList.toString());
                quizzes.clear();
            }
        }

        //4.3.9 Verifying operators input
        if (add == null
                && subtract == null
                && multiply == null
                && divide == null) {
            errorsList.add("<br> <b>Error #" + errorCounter + "</b> on operators: " + "empty input");
            repeatIntake = true;
            errorCounter++;
        }

        //4.2.12 If input is correct, save input in list
        if (!repeatIntake) {
            quizInput.clear();
            quizInput.add(quizName);
            quizInput.add(highestNumber);
            quizInput.add(numbersInExpression);
            quizInput.add(numberOfExpressions);
            quizInput.add(fixedResult);
            quizInput.add(add);
            quizInput.add(subtract);
            quizInput.add(multiply);
            quizInput.add(divide);
        }

        return repeatIntake;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        //5. Get error position and reset lists of errors
        getErrorPosition();
        resetErrorList();

        //6. Command processes

        //6.1 Receiving command parameter
        switch (req.getParameter("command")) {


            //6.2 Generating new quiz
            case "generateQuiz":

                //6.2.1 Fetching quiz type
                quizType = req.getParameter("quizType");

                //6.2.2 Determining quiz type
                switch (quizType) {


                    //6.2.2.1 Verifying user input and generating random quiz
                    case "randomQuiz":

                        //6.2.2.1.1 Resetting values for each iteration
                        repeatIntake = false;
                        resetErrorList();
                        resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //6.2.2.1.2 Checking user input and generating quiz
                        checkInputAndGenerateRandomQuiz(req.getParameter("quizName"),
                                req.getParameter("highestNumber"),
                                req.getParameter("numbersInExpression"),
                                req.getParameter("numberOfExpressions"),
                                req.getParameter("Integer1"),
                                req.getParameter("Double1"),
                                req.getParameter("add"),
                                req.getParameter("subtract"),
                                req.getParameter("multiply"),
                                req.getParameter("divide"));

                        //6.2.2.1.3 If user input is incorrect, repeat input intake
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

                        //6.2.2.1.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd5 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd5.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    //6.2.2.2 Verifying user input and generating ResultRangeQuiz
                    case "resultRangeQuiz":

                        //6.2.2.2.1 Resetting values
                        repeatIntake = false;
                        resetErrorList();
                        resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //6.2.2.2.2 Checking user input and generating quiz
                        checkInputAndGenerateResultRangeQuiz(
                                req.getParameter("quizName"),
                                req.getParameter("highestNumber"),
                                req.getParameter("numbersInExpression"),
                                req.getParameter("numberOfExpressions"),
                                req.getParameter("Integer1"),
                                req.getParameter("Double1"),
                                req.getParameter("resultMin"),
                                req.getParameter("resultMax"),
                                req.getParameter("add"),
                                req.getParameter("subtract"),
                                req.getParameter("multiply"),
                                req.getParameter("divide"));

                        //6.2.2.2.3 If user input is incorrect, repeat input intake
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

                        //6.2.2.2.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd5 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd5.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }

                        //6.2.2.3 Verifying fixedResultQuiz
                    case "fixedResultQuiz":

                        //6.2.2.3.1 Resetting values for each iteration
                        repeatIntake = false;
                        resetErrorList();
                        resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //6.2.2.3.2 Checking user input and generating quiz
                        checkInputAndGenerateFixedResultQuiz(req.getParameter("quizName"),
                                req.getParameter("highestNumber"),
                                req.getParameter("numbersInExpression"),
                                req.getParameter("numberOfExpressions"),
                                req.getParameter("fixedResult"),
                                req.getParameter("add"),
                                req.getParameter("subtract"),
                                req.getParameter("multiply"),
                                req.getParameter("divide"));

                        System.out.println(quizzes.get(0).getQuizName());


                        //6.2.2.3.3 If user input is incorrect, repeat input intake
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

                        //6.2.2.3.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
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
                break;

            //6.3 Generating new quiz based on same input
            case "generateAgain":

                //6.3.1 Get quizType from previous input
                quizType = quizzes.get(0).getQuizType();

                //6.3.2 Clearing lists so that details from previous generation are deleted
                resetQuizzesList();
                Generator.clearAllGeneratorLists();

                //6.3.3 Select quizType based on previous quizType
                switch (quizType) {

                    //6.3.3.1 Generate randomQuiz based on previous quizType
                    case "randomQuiz":

                        //6.3.3.1.1 Reset values and lists
                        resetErrorList();
                        resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //6.3.3.1.2 Generate new quiz based on previous quizType
                        checkInputAndGenerateRandomQuiz(quizInput.get(0),
                                quizInput.get(1),
                                quizInput.get(2),
                                quizInput.get(3),
                                quizInput.get(4),
                                quizInput.get(5),
                                quizInput.get(6),
                                quizInput.get(7),
                                quizInput.get(8),
                                quizInput.get(9));


                        //6.3.3.1.3 Redirect back to randomQuiz if input is invalid
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

                        //6.3.3.1.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    //6.3.3.2 Verifying resultRangeQuiz
                    case "resultRangeQuiz":

                        //6.3.3.2.1 Reset values and lists
                        repeatIntake = false;
                        resetErrorList();
                        resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //6.3.3.2.2 Generate new quiz based on previous quizType
                        checkInputAndGenerateResultRangeQuiz(quizInput.get(0),
                                quizInput.get(1),
                                quizInput.get(2),
                                quizInput.get(3),
                                quizInput.get(4),
                                quizInput.get(5),
                                quizInput.get(6),
                                quizInput.get(7),
                                quizInput.get(8),
                                quizInput.get(9),
                                quizInput.get(10),
                                quizInput.get(11));
                        errorsList.clear();

                        //6.3.3.2.3 Redirect back to randomQuiz if input is invalid
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

                        //6.3.3.2.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
                            } catch (ServletException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;


                    //6.3.3.3 Verifying fixedResultQuiz
                    case "fixedResultQuiz":

                        //6.3.3.3.1 Reset values and lists
                        repeatIntake = false;
                        resetErrorList();
                        resetQuizzesList();
                        Generator.clearAllGeneratorLists();

                        //6.3.3.3.2 Generate new quiz based on previous quizType
                        checkInputAndGenerateFixedResultQuiz(quizInput.get(0),
                                quizInput.get(1),
                                quizInput.get(2),
                                quizInput.get(3),
                                quizInput.get(4),
                                quizInput.get(5),
                                quizInput.get(6),
                                quizInput.get(7),
                                quizInput.get(8));
                        errorsList.clear();

                        //6.3.3.3.3 Redirect back to randomQuiz if input is invalid
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

                        //6.3.3.3.4 Redirect to results page once command is completed
                        else {
                            RequestDispatcher rd6 = req.getRequestDispatcher("/Results/Results.jsp");
                            try {
                                rd6.forward(req, resp);
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
                break;

            //6.4 Save quiz in database
            case "saveQuizInDB":

                    Database.saveInDB(quizzes.get(0));
                    //6.4.2
                    RequestDispatcher rd = req.getRequestDispatcher("/Results/SavedQuiz.jsp");
                    try {
                        rd.forward(req, resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;

            //6.5 Back to menu
            case "backToMenu":

                //6.5.1 Reset quiz & expression lists
                resetQuizzesList();
                Generator.clearAllGeneratorLists();

                //6.5.2 Redirect back to Menu
                RequestDispatcher rd7 = req.getRequestDispatcher("/Navigation/Menu.html");
                try {
                    rd7.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            //6.6 Get results quiz from database:
            case "dbQuizName":

                //6.6.1 Pulling quiz fromDB
                getFromDB(req.getParameter("quizName"));

                //6.6.2 Redirect to results page once commands are completed and
                RequestDispatcher rd4 = req.getRequestDispatcher("/Results/ExistingQuizResults.jsp");
                try {
                    rd4.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            default:
                break;
        }

    }
}