package FrontEnd;

import Backend.Engine.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet("/Adaptor")
public class Adaptor extends HttpServlet {

    //1. Variables

    //1.1 Primitive variables
    int highestNumber;
    int numberOfExpressions;
    int fixedIntResult;
    public static int errorCounter = 1;
    double fixedDoubleResult;
    double resultMin;
    double resultMax;
    public static boolean repeatIntake = false;
    boolean emptyQuizName = false;
    boolean emptyHighestNumber = false;
    boolean emptyNumberOfExpressions = false;
    boolean emptyResultType = false;
    boolean emptyFixedIntResult = false;
    boolean emptyFixedDoubleResult = false;
    boolean emptyResultMin = false;
    boolean emptyResultMax = false;

    //1.2 String variables
    String quizName;
    String resultType;
    String errorMessage;
    String quizType;
    String fixedResult;

    //1.3 Lists
    public static List<String> errorsList = new ArrayList<>();

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

        }
        //1.4.12 Determining issues
        if (isMultipleDots) {
            errorsList.add("Multiple dots");
        }
        if (isWrongInput) {
            errorsList.add("Non-digit or non-dot");
        }
        if (isDotFirst) {
            errorsList.add("Dot first");
        }
        if (isDotLast) {
            errorsList.add("Dot last");
        }
        if (isEmptyInput) {
            errorsList.add("Empty input");
        }
        if (!isMultipleDots && !isWrongInput && !isDotFirst && !isDotLast && !isEmptyInput) {
            errorsList.add("Good input");
        }

//
//        //2.12.12 Determining issues with user input
//        for (int j = 0; j < errorsList.size(); j++) {
//            switch (errorsList.get(j)) {
//
//                case "Multiple dots":
//
//                    System.out.println("Error #" + errorCounter + ": Too many dots: ");
//                    for (int i = 0; i < wrongDotPosition.size(); i++) {
//                        System.out.println("additional dot #" + (i + 1) + " on position " + wrongDotPosition.get(i));
//                    }
//                    errorCounter++;
//                    break;
//
//                case "Non-digit & non-dot":
//
//                    for (int i = 0; i < wrongCharPosition.size(); i++) {
//                        System.out.println("Error #" + errorCounter + ": You inserted following wrong character: "
//                                + "'" + wrongChar.get(i) + "'" + " on position: " + wrongCharPosition.get(i));
//                        errorCounter++;
//                    }
//                    break;
//
//                case "Dot first":
//                    System.out.println("Error #" + errorCounter + ": Dot is on first position");
//                    errorCounter++;
//                    break;
//
//                case "Dot last":
//                    System.out.println("Error #" + errorCounter + ": Dot is on last position");
//                    errorCounter++;
//                    break;
//                case "Empty input":
//                    System.out.println("Error #" + errorCounter + ": Empty input");
//                    errorCounter++;
//                    break;
//
//                case "Good input":
//                    System.out.println("Good input");
//                    break;
//
//                default:
//                    break;
//            }
//        }
        return errorsList;
    }

    static public void resetErrorList(){
        errorsList.clear();
        errorCounter = 1;
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

                //4.1.1 Verifying quiz name input - if is empty, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("quizName")).size(); i++) {
                    if (checkInput(req.getParameter("quizName")).get(i).equals("Empty input")) {
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.2 Verifying highest number input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("highestNumber")).size(); i++) {
                    if (!checkInput(req.getParameter("highestNumber")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.3 Verifying number of expressions input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("numberOfExpressions")).size(); i++) {
                    if (!checkInput(req.getParameter("numberOfExpressions")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.4 Verifying radio box for int/double
                if (req.getParameter("Integer1") == null && req.getParameter("Double1") == null) {
                    errorsList.add("Error #" + errorCounter + " on result type: " + "Empty input");
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
                break;

            //4.2 Verifying resultRangeQuiz
            case "resultRangeQuiz":

                resetErrorList();

                //4.1.1 Verifying quiz name input - if is empty, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("quizName")).size(); i++) {
                    if (checkInput(req.getParameter("quizName")).get(i).equals("Empty input")) {
                        errorsList.add("Error #" + errorCounter + " on quiz name: " + checkInput(req.getParameter("quizName")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.2 Verifying highest number input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("highestNumber")).size(); i++) {
                    if (!checkInput(req.getParameter("highestNumber")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on highest number: " + checkInput(req.getParameter("highestNumber")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.3 Verifying number of expressions input - if is empty or non-digit, repeat intake and save error message
                for (int i = 0; i < checkInput(req.getParameter("numberOfExpressions")).size(); i++) {
                    if (!checkInput(req.getParameter("numberOfExpressions")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on number of expressions: " + checkInput(req.getParameter("numberOfExpressions")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.4 Verifying radio box for int/double
                if (req.getParameter("Integer1") == null && req.getParameter("Double1") == null) {
                    errorsList.add("Error #" + errorCounter + " on result type: " + "Empty input");
                    repeatIntake = true;
                    errorCounter++;
                }

                //4.1.5 Verifying minimum result range
                for (int i = 0; i < checkInput(req.getParameter("resultMin")).size(); i++) {
                    if (!checkInput(req.getParameter("resultMin")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on minimum range result: " + checkInput(req.getParameter("resultMin")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }
                }

                //4.1.6 Verifying maximum result range
                for (int i = 0; i < checkInput(req.getParameter("resultMax")).size(); i++) {
                    if (!checkInput(req.getParameter("resultMax")).get(i).equals("Good input")
                    ) {
                        errorsList.add("Error #" + errorCounter + " on maximum range result: " + checkInput(req.getParameter("resultMax")).get(i));
                        repeatIntake = true;
                        errorCounter++;
                    }


                    //4.1.7 Redirect back to randomQuiz if input is invalid
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

            case "fixedResultQuiz":
                break;

            default:
                break;
        }
//

//
//        //Verify quiz type
//        emptyQuizName;
//        emptyHighestNumber;
//        emptyNumberOfExpressions;
//        emptyResultType;
//        emptyFixedIntResult;
//        emptyFixedDoubleResult;
//        emptyResultMin;
//        emptyResultMax;

//        //1. type: Name + resultType
//        if (emptyQuizName != false
//                && emptyHighestNumber == false
//                && emptyNumberOfExpressions == false
//                && emptyResultType == true
//                && emptyFixedIntResult == true
//                && emptyFixedDoubleResult == true
//                && emptyResultMin == true
//                && emptyResultMax == true) {
//
//        }
//        //2. type: Name + resultInt
//        if (emptyQuizName != false
//                && emptyHighestNumber == false
//                && emptyNumberOfExpressions == false
//                && emptyResultType == true
//                && emptyFixedIntResult == false
//                && emptyFixedDoubleResult == true
//                && emptyResultMin == true
//                && emptyResultMax == true) {
//        }
//
//        //3. type: Name + resultDouble
//        if (emptyQuizName != false
//                && emptyHighestNumber == false
//                && emptyNumberOfExpressions == false
//                && emptyResultType == true
//                && emptyFixedIntResult == true
//                && emptyFixedDoubleResult == false
//                && emptyResultMin == true
//                && emptyResultMax == true) {
//        }
//
//        //4. type: Name + resultType + resultRange
//        if ((emptyQuizName != false
//                && emptyHighestNumber == false
//                && emptyNumberOfExpressions == false
//                && emptyResultType == true
//                && emptyFixedIntResult == false
//                && emptyFixedDoubleResult == false
//                && emptyResultMin == true
//                && emptyResultMax == true)
//                ||
//                emptyQuizName != false
//                        && emptyHighestNumber == false
//                        && emptyNumberOfExpressions == false
//                        && emptyResultType == true
//                        && emptyFixedIntResult == true
//                        && emptyFixedDoubleResult == false
//                        && emptyResultMin == true
//                        && emptyResultMax == true) {
//        }
//
//
//
//        //test to see them
//        System.out.println(quizName);
//        System.out.println(highestNumber);
//        System.out.println(numberOfExpressions);
//        System.out.println(resultType);
//        System.out.println(fixedIntResult);
//        System.out.println(fixedDoubleResult);
//        System.out.println(resultMin);
//        System.out.println(resultMax);


        //2.6 Creating object based on a constructor that takes a list of items


        // 2.6.1 Taking repeat intake from quiz constructor which tells whether info is correct
        //repeatIntake = quiz.isRepeatIntake();


    }

}