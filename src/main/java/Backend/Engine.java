package Backend;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Engine {

    /**
     * --------------------------------------------------------------------------------
     * 1. Declaring & initializing variables and lists
     * --------------------------------------------------------------------------------
     */

    //1.1 Primitive variables
    public static int myQuizTime;
    public static int score = 0;
    public static int nrOfLevels = 0;
    static int maxNumber = 15;
    static int numberListSize = 7;

    //1.2 Time variables
    public static LocalDate date = LocalDate.now();
    public static LocalTime startTime;
    public static LocalTime endTime;

    //1.3 Math variables
    public static Random randomGenerator = new Random();
    static ScriptEngineManager sem = new ScriptEngineManager();

    //1.4 String variables
    static String a = "+";
    static String b = "-";
    static String c = "*";
    static String d = "/";
    static public StringBuilder tempString = new StringBuilder();

    //1.5 Lists
    static public List<Integer> numbersList = new ArrayList<>();
    static public List<String> operatorsList = new ArrayList<>();
    static public List<String> oneExpression = new ArrayList<>();
    static public List<String> allExpressions = new ArrayList<>();
    static public String[] defaultOperators = {"+", "-", "*", "/"};

    /**
     * --------------------------------------------------------------------------------
     * 2. Declaring methods
     * --------------------------------------------------------------------------------
     */

    //2.1 Generating a list of numbers based on a maximum number and a defined list size
    static public void generateNumberList(int maxNumber, int listSize) {
        numbersList.clear();
        for (int i = 0; i != listSize - 1; i++) {
            numbersList.add(randomGenerator.nextInt(maxNumber) + 1);
        }
    }

    //2.2 Generating a list of operators based on user input which is the parameter in the method signature
    public static void generateOperatorList(String... operators) {
        operatorsList.clear();
        for (int i = 0; i < operators.length; i++) {
            if (operators[i] != null) operatorsList.add(operators[i]);
        }
    }

    //2.3 Generating an expression based on the number and operator list methods above (2.1 & 2.2)
    static void generateExpression() {
        boolean stop=false;

        //2.3.1 Opening loop for making sure that the expression generated has an integral result
        do {

            //2.3.2 Clearing the list containing one expression to leave only the newly generated one
            oneExpression.clear();

            //2.3.3 Generating new expression
            generateNumberList(maxNumber, numberListSize);
            generateOperatorList(a, b, c, d);

            //2.3.4 Distributing numbers and operators evenly in the expression
            int nrCounter = 0;
            for (int currentNr = 0; currentNr < numberListSize + operatorsList.size() - 1; currentNr++) {
                if (currentNr % 2 == 0) {
                    oneExpression.add(String.valueOf(numbersList.get(nrCounter)));
                    nrCounter++;
                } else
                    oneExpression.add(operatorsList.get(randomGenerator.nextInt(operatorsList.size())));
            }

            //2.3.5 If operator is at the end of the expression, remove last index in the expression
            removeOddOperator();

            //2.3.6 Here is where it is checking if expression has integral result. If it has, stop the loop
            try {
                if(calculateExpression(ExpressionToString(oneExpression))%2==0){
                    stop=true;
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        while(!stop);
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
    static public void generateAllExpressions(int nrOfExpressions) {
        for (int i = 0; i < nrOfExpressions; i++) {
            //2.6.1 Re-creating a new expression for each iteration
            generateExpression();
            //2.6.2 Using "expressionToStringMethod" to not add anything else into the list besides numbers and operators
            allExpressions.add(ExpressionToString(oneExpression));
        }
    }


    //2.7 Method for taking an expression and putting it in a String
    static public String ExpressionToString(List<String> anyExpression) {
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


    public static void main(String[] args) {

        generateAllExpressions(5);
        for (String allExpression : allExpressions) {
            System.out.println(allExpression);
            try {
                System.out.println(calculateExpression(allExpression));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }

    }
}
