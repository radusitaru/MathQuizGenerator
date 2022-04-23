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


    //1.2 Time variables
    public static LocalDate date = LocalDate.now();
    public static LocalTime startTime;
    public static LocalTime endTime;

    //1.3 Math variables
    public static Random randomGenerator = new Random();
    static ScriptEngineManager sem = new ScriptEngineManager();

    //1.4 String variables
    static public StringBuilder tempString = new StringBuilder();

    //1.5 Lists & arrays
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

    //2.0 Clear all lists
    static public void clearAllGeneratorLists(){
        numbersList.clear();
        operatorsList.clear();
        oneExpression.clear();
        allExpressions.clear();
        allExpressionsResults.clear();
        allExpressionsAndResults.clear();
    }

    //2.1 Generating a list of numbers based on a maximum number and a defined list size
    static public void generateNumberList() {
        numbersList.clear();
        for (int i = 0; i < Adaptor.quizzes.get(0).getNumbersInExpression(); i++) {
            numbersList.add(randomGenerator.nextInt(Adaptor.quizzes.get(0).getHighestNumber()) + 1);
        }
    }

    //2.2 Generating a list of operators based on user input which is the parameter in the method signature
    public static void generateOperatorList() {
        operatorsList.clear();
        for (int i = 0; i < Adaptor.inputOperators.size(); i++) {
            if (Adaptor.inputOperators.get(i) != null) operatorsList.add(Adaptor.inputOperators.get(i));
        }
    }

    /*2.3 Generating an expression based on the number and operator list methods above (2.1 & 2.2)
          In this section there are many overloaded methods of generateExpression */


    //2.3.1 Generating an expression based on a chosen data type (int or double)
    static void randomExpression(String resultType) {

        boolean stop = false;


        //2.3.1.1 Opening loop for making sure that the expression generated has an integral result
        do {

            //2.3.1.2 Clearing the list containing one expression to leave only the newly generated one
            oneExpression.clear();

            //2.3.1.3 Generating new expression
            generateNumberList();
            generateOperatorList();

            //2.3.1.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < Adaptor.quizzes.get(0).getNumbersInExpression() + operatorsList.size() - 1; currentNr++) {
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }

            //2.3.1.5 If operator is at the end of the expression, remove last index in the expression
            removeOddOperator();

            //2.3.1.6 Here is where it is checking if expression has an int result. If it has, stop the loop
            if (resultType.equalsIgnoreCase("Integer1")) {
                try {
                    if (calculateExpression(expressionToString(oneExpression)) % 1 == 0) {
                        stop = true;
                    }
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
            }

            //2.3.1.7 Here is where it is checking if expression has a double result. If it has, stop the loop
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
        try {
            allExpressionsResults.add(String.valueOf(calculateExpression(expressionToString(oneExpression))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //2.3.4 Generating an expression based on a chosen range, min and max, and of a given data type, int or double
    static void resultRangeExpression(String resultType, double min, double max) {
        boolean stop = false;

        //2.3.4.1 Opening loop for making sure that the expression generated has an integral result
        do {

            //2.3.4.2 Clearing the list containing one expression to leave only the newly generated one
            oneExpression.clear();

            //2.3.4.3 Generating new expression
            generateNumberList();
            generateOperatorList();

            //2.3.4.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < Adaptor.quizzes.get(0).getNumbersInExpression() + operatorsList.size() - 1; currentNr++) {
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }

            //2.3.4.5 If operator is at the end of the expression, remove last index in the expression
            removeOddOperator();

            //2.3.4.6 Here is where it is checking if expression has an int result and whether it is in the desired range
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

            //2.3.4.7 Here is where it is checking if expression has a double result and whether it is in the desired range
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
        try {
            allExpressionsResults.add(String.valueOf(calculateExpression(expressionToString(oneExpression))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //2.3.6 Generating an expression based on the the desired result (double)
    static void fixedResultExpression(double result) {
        boolean stop = false;

        //2.3.6.1 Opening loop for making sure that the expression generated has an integral result
        do {

            //2.3.6.2 Clearing the list containing one expression to leave only the newly generated one
            oneExpression.clear();

            //2.3.6.3 Generating new expression
            generateNumberList();
            generateOperatorList();

            //2.3.6.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < Adaptor.quizzes.get(0).getNumbersInExpression() + operatorsList.size() - 1; currentNr++) {
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }
            //2.3.6.5 If operator is at the end of the expression, remove last index in the expression
            removeOddOperator();

            //2.3.6.6 Here is where it is checking if expression has the desired result
            try {
                //2.3.6.7 Calculating the length of the result desired by the user
                int resultLength = String.valueOf(result).length();
                //2.3.6.8 Converting expression result to String (see 2.3.6.9 to understand why)
                String expressionResult = String.valueOf((calculateExpression(expressionToString(oneExpression))));
                /*2.3.6.9 Shortening expressionResult and input by user (this is so that generator can find expressions)
                          That is achieved by substring-ing the results String by the length of desired result */
                if (expressionResult.equals(String.valueOf(result))) {
                    stop = true;
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        while (!stop);
        try {
            allExpressionsResults.add(String.valueOf(calculateExpression(expressionToString(oneExpression))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    //2.4 Method for removing the odd operator at the end of the expression
    static public void removeOddOperator() {
        //2.4.1 First I'm checking if last index in the position is an operator
        if (checkIfStringIsOperator(oneExpression.get(oneExpression.size() - 1)) == true) {
            //2.4.2 If it is, I'm removing it
            oneExpression.remove(oneExpression.size() - 1);
        }
    }

    //2.5 Method for checking if a String is an operator (+,-,*,/)
    static public boolean checkIfStringIsOperator(String string) {
        boolean isOperator = false;
        //2.5.1 Iterate through the array of all possible operators. If it is found, return boolean answer
        for (int i = 0; i < defaultOperators.length; i++) {
            if (string.equals(defaultOperators[i])) isOperator = true;
        }
        return isOperator;
    }

    //2.6 Generating all expressions based on the desired amount => see parameter of method
    static public String generateAllExpressions() {
        allExpressionsAndResults.clear();
        oneExpression.clear();
        operatorsList.clear();
        numbersList.clear();

        for (int i = 0; i < Adaptor.quizzes.get(0).getNumberOfExpressions(); i++) {

            //2.6.0 Generating numbers and operators
            generateNumberList();
            generateOperatorList();

            //2.6.1 Generating correct expressions based on quiz type

            switch (Adaptor.quizzes.get(0).getQuizType()) {
                case "randomQuiz":
                    randomExpression(Adaptor.quizzes.get(0).getResultType());
                    break;

                case "fixedResultQuiz":
                    fixedResultExpression(Adaptor.quizzes.get(0).getFixedResult());
                    break;

                case "resultRangeQuiz":
                    resultRangeExpression(Adaptor.quizzes.get(0).getResultType(), Adaptor.quizzes.get(0).getResultMin(), Adaptor.quizzes.get(0).getResultMax());
                    break;
            }


            //2.6.2 Using "expressionToStringMethod" to not add anything else into the list besides numbers and operators
            allExpressions.add(expressionToString(oneExpression));
        }
        return allExpressions.toString();
    }


    static public void generateAllExpressionsAndResults(){
        allExpressionsAndResults.clear();
        generateAllExpressions();
        for(int i = 0;i< allExpressions.size();i++){
            allExpressionsAndResults.add("Expression #"+(i+1)+": "+allExpressions.get(i)+" , Result: "+allExpressionsResults.get(i));
        }
    }


    //2.7 Method for taking an expression and putting it in a String
    static public String expressionToString(List<String> anyExpression) {
        tempString.delete(0, tempString.length());
        for (int i = 0; i < anyExpression.size(); i++) {
            tempString.append(anyExpression.get(i));
        }
        return tempString.toString();
    }

    //2.8 Calculate result of generated expression and compare it with the result input by the user
    public static double calculateExpression(String expression) throws ScriptException {

        ScriptEngine level = sem.getEngineByName("JavaScript");
        String generatedExpressionResult = String.valueOf(level.eval(String.valueOf(expression)));
        return Double.parseDouble(generatedExpressionResult);
    }

    //2.9 Calculating user score
    public static double score(double score, double nrOfLevels) {

        //calculating score out of 100
        System.out.println("Your score: " + (score / nrOfLevels) * 100);
        return (score / nrOfLevels) * 100;
    }

    //2.10 Getting current time
    public static LocalTime getTime() {
        return LocalTime.now();
    }


    //2.11 Calculating time spent on quiz
    public static long quizTime(LocalTime endTime, LocalTime startTime) {
        System.out.println("You spent " + ChronoUnit.SECONDS.between(endTime, startTime) + " seconds on completing the quiz");
        return ChronoUnit.SECONDS.between(endTime, startTime);


    }


    }
