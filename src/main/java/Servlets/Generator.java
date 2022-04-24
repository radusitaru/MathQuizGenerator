package Servlets;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//
@WebServlet("/Generator")
public class Generator extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
    }

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables
     * --------------------------------------------------------------------------------
     */


    //1.1 Time variables
    public static LocalDate date = LocalDate.now();

    //1.2 Math variables
    public static Random randomGenerator = new Random();
    static ScriptEngineManager sem = new ScriptEngineManager();

    //1.3 String variables
    static public StringBuilder tempString = new StringBuilder();

    //1.4 Lists & arrays
    static public List<Integer> numbersList = new ArrayList<>();
    static public List<String> operatorsList = new ArrayList<>();
    static public List<String> oneExpression = new ArrayList<>();
    static public List<String> allExpressions = new ArrayList<>();
    static public List<String> allExpressionsResults = new ArrayList<>();
    static public List<String> allExpressionsAndResults = new ArrayList<>();
    static public String[] defaultOperators = {"+", "-", "*", "/"};

    /**
     * --------------------------------------------------------------------------------
     * 2. Methods
     * --------------------------------------------------------------------------------
     */


    //2.1 Clear all lists
    static public void clearAllGeneratorLists() {
        numbersList.clear();
        operatorsList.clear();
        oneExpression.clear();
        allExpressions.clear();
        allExpressionsResults.clear();
        allExpressionsAndResults.clear();
    }

    //2.2 Generating a list of numbers based on a maximum number and a defined list size
    static public void generateNumberList() {
        numbersList.clear();
        for (int i = 0; i < Driver.quizzes.get(0).getNumbersInExpression(); i++) {
            numbersList.add(randomGenerator.nextInt(Driver.quizzes.get(0).getHighestNumber()) + 1);
        }
    }

    //2.3 Generating a list of operators based on user input which is the parameter in the method signature
    public static void generateOperatorList() {
        operatorsList.clear();
        for (int i = 0; i < Driver.inputOperators.size(); i++) {
            if (Driver.inputOperators.get(i) != null) operatorsList.add(Driver.inputOperators.get(i));
        }
    }

    //2.4 Generating an expression for randomQuiz type
    static void randomExpression(String resultType) {

        boolean stop = false;


        //2.4.1 Opening loop for making sure that the expression generated has an integral result
        do {

            //2.4.2 Clearing the list containing oneExpression to leave only the newly generated one
            oneExpression.clear();

            //2.4.3 Generating new expression
            generateNumberList();
            generateOperatorList();

            //2.4.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < Driver.quizzes.get(0).getNumbersInExpression() + operatorsList.size() - 1; currentNr++) {
                //2.4.4.1 By using module %2, the operators and numbers are spread equally
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }

            //2.4.5 If operator is at the end of the expression, remove last index in the expression with below method
            removeOddOperator();

            //2.4.6 In case that integer result is wanted, stop the loop if expression has a double result.
            if (resultType.equalsIgnoreCase("Integer1")) {
                try {
                    if (calculateExpression(expressionToString(oneExpression)) % 1 == 0) {
                        stop = true;
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }

            //2.4.7 In case that double result is wanted, stop the loop if expression has a double result.
            if (resultType.equalsIgnoreCase("Double1")) {
                try {
                    if (calculateExpression(expressionToString(oneExpression)) % 1 != 0) {
                        stop = true;
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
        }

        while (!stop);

        //2.4.8 Once expression exited loop, add its result to results list.
        try {
            allExpressionsResults.add(String.valueOf(calculateExpression(expressionToString(oneExpression))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //2.5 Generating an expression for resultRangeQuiz
    static void resultRangeExpression(String resultType, double min, double max) {
        boolean stop = false;

        //2.5.1 Opening loop for making sure that the expression generated has the desired result type, int or double
        do {

            //2.5.2 Clearing the list containing one expression to leave only the newly generated one
            oneExpression.clear();

            //2.5.3 Generating a new expression
            generateNumberList();
            generateOperatorList();

            //2.5.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < Driver.quizzes.get(0).getNumbersInExpression() + operatorsList.size() - 1; currentNr++) {
                //2.5.4.1 By using module %2, the operators and numbers are spread equally
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }

            //2.5.5 If operator is at the end of the expression, remove last index in the expression
            removeOddOperator();

            //2.5.6 In case that integer result is wanted, stop the loop if expression has a double result.
            if (resultType.equalsIgnoreCase("Integer1")) {
                try {
                    if (calculateExpression(expressionToString(oneExpression)) % 1 == 0
                            && calculateExpression(expressionToString(oneExpression)) >= min
                            && calculateExpression(expressionToString(oneExpression)) <= max) {
                        stop = true;
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }

            //2.5.7 In case that double result is wanted, stop the loop if expression has a double result.
            if (resultType.equalsIgnoreCase("Double1")) {
                try {
                    if (calculateExpression(expressionToString(oneExpression)) % 1 != 0
                            && calculateExpression(expressionToString(oneExpression)) >= min
                            && calculateExpression(expressionToString(oneExpression)) <= max) {
                        stop = true;
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }
        }
        while (!stop);

        //2.5.8 Once expression exited loop, add its result to results list.
        try {
            allExpressionsResults.add(String.valueOf(calculateExpression(expressionToString(oneExpression))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //2.6 Generating an expression for resultRangeQuiz
    static void fixedResultExpression(double result) {
        boolean stop = false;

        //2.6.1 Opening loop for making sure that the expression generated has the desired result type, int or double
        do {

            //2.6.2 Clearing the list containing one expression to leave only the newly generated one
            oneExpression.clear();

            //2.6.3 Generating new expression
            generateNumberList();
            generateOperatorList();

            //2.6.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < Driver.quizzes.get(0).getNumbersInExpression() + operatorsList.size() - 1; currentNr++) {
                //2.6.4.1 By using module %2, the operators and numbers are spread equally
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }
            //2.6.5 If operator is at the end of the expression, remove last index in the expression
            removeOddOperator();

            //2.6.6 Checking if expression has the desired result
            try {
                //2.6.6.1 Converting expression result to String
                String expressionResult = String.valueOf((calculateExpression(expressionToString(oneExpression))));

                //2.6.6.2 Comparing expression result vs user desired result, stop loop if true
                if (expressionResult.equals(String.valueOf(result))) {
                    stop = true;
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        while (!stop);

        //2.6.7 Once expression exited loop, add its result to results list.
        try {
            allExpressionsResults.add(String.valueOf(calculateExpression(expressionToString(oneExpression))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //2.7 Method for removing the odd operator at the end of the expression
    static public void removeOddOperator() {
        //2.7.1 First I'm checking if last index in the position is an operator
        if (checkIfStringIsOperator(oneExpression.get(oneExpression.size() - 1)) == true) {
            //2.7.2 If it is, I'm removing it
            oneExpression.remove(oneExpression.size() - 1);
        }
    }

    //2.8 Method for checking if a String is an operator (+,-,*,/)
    static public boolean checkIfStringIsOperator(String string) {
        boolean isOperator = false;
        //2.8.1 Iterate through the array of all possible operators. If it is found, return boolean answer
        for (int i = 0; i < defaultOperators.length; i++) {
            if (string.equals(defaultOperators[i])) isOperator = true;
        }
        return isOperator;
    }

    //2.9 Generating all expressions
    static public String generateAllExpressions() {
        clearAllGeneratorLists();

        for (int i = 0; i < Driver.quizzes.get(0).getNumberOfExpressions(); i++) {

            //2.9.1 Generating numbers and operators
            generateNumberList();
            generateOperatorList();

            //2.9.2 Generating correct expressions based on quiz type
            switch (Driver.quizzes.get(0).getQuizType()) {
                case "randomQuiz":
                    randomExpression(Driver.quizzes.get(0).getResultType());
                    break;

                case "fixedResultQuiz":
                    fixedResultExpression(Driver.quizzes.get(0).getFixedResult());
                    break;

                case "resultRangeQuiz":
                    resultRangeExpression(Driver.quizzes.get(0).getResultType(), Driver.quizzes.get(0).getResultMin(), Driver.quizzes.get(0).getResultMax());
                    break;
            }


            //2.9.3 Using "expressionToStringMethod" to not add anything else into the list besides numbers and operators
            allExpressions.add(expressionToString(oneExpression));
        }

        return allExpressions.toString();
    }

    //2.10 Concatenate expressions and results
    static public void concatenateExpressionsAndResults() {
        generateAllExpressions();
        for (int i = 0; i < allExpressions.size(); i++) {
            allExpressionsAndResults.add("<br><b>" + "Expression #" + (i + 1) + ":</b> " + allExpressions.get(i) + "    =    " + allExpressionsResults.get(i));
        }
    }

    //2.11 Method for taking an expression and putting it in a String
    static public String expressionToString(List<String> anyExpression) {
        tempString.delete(0, tempString.length());
        for (int i = 0; i < anyExpression.size(); i++) {
            tempString.append(anyExpression.get(i));
        }
        return tempString.toString();
    }

    //2.12 Calculate result of generated expression
    public static double calculateExpression(String expression) throws ScriptException {

        ScriptEngine level = sem.getEngineByName("JavaScript");
        String generatedExpressionResult = String.valueOf(level.eval(String.valueOf(expression)));
        return Double.parseDouble(generatedExpressionResult);
    }

    //2.11 Calculating user score
    public static double score(double score, double nrOfLevels) {

        //2.11.1 Calculating score out of 100
        System.out.println("Your score: " + (score / nrOfLevels) * 100);
        return (score / nrOfLevels) * 100;
    }

    //2.12 Getting current time
    public static LocalTime getTime() {
        return LocalTime.now();
    }


    //2.13 Calculating time spent on quiz
    public static long quizTime(LocalTime endTime, LocalTime startTime) {
        System.out.println("You spent " + ChronoUnit.SECONDS.between(endTime, startTime) + " seconds on completing the quiz");
        return ChronoUnit.SECONDS.between(endTime, startTime);
    }
}
