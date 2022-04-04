package BackEnd;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import static java.lang.Double.parseDouble;


/**
 * QUIZ: Generate mathematical expressions divided in levels, which the user must solve in a limit of time
 * <p>
 * - automatically generate expressions with random operators and random numbers
 * - Do not allow 0 to be generated in the expressions,
 * - generate only expressions that have integral results
 * - allow superuser to select what type of operators to include in the generated expressions,
 * - allow superuser to select maximum number value that will be used in the expressions
 * - allow superuser to set limit of time for each level,
 * - automatically save each program run in database, including the expressions, score and time spent,
 * - after each program run, display final score, time & ranking against all the other results saved in the database,
 * - web UI interface,
 */


/**
 * --------------------------------------------------------------------------------
 * MAIN
 * --------------------------------------------------------------------------------
 */
public class Main extends GenerateExpression {

    public static void Main(String[] args) throws ScriptException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //get name of player/user
        name = getName();

        //get start time
        startTime = getTime();

        //run math levels - they can be modified by using different numbers/operators/methods
        calculateExpression(generateTwoOpLevel(1, 10, "+", "-"));
//        runLevel(generateTwoOpLevel(2, 3, "*", "/"));
//        runLevel(generateFourOpLevel(3, 3, "+", "-", "*", "/"));
//        runLevel(generateTwoOpLevel(4, 5, "*", "/"));
//        runLevel(generateFourOpLevel(5, 5, "+", "-", "*", "/"));

        //get end time
        endTime = getTime();

        //save details of quiz in database
//        Database.saveInDB(name, date, score(score, nrOfLevels), quizTime(endTime, startTime), expressions[0], expressions[1], expressions[2], expressions[3], expressions[4]);

//        Database.getFromDB();
    }

    /**
     * --------------------------------------------------------------------------------
     * Declare & initialize global variables
     * --------------------------------------------------------------------------------
     */
    public static String name;

    public static int score = 0;
    public static int nrOfLevels = 0;

    static Scanner sc = new Scanner(System.in);

    static ScriptEngineManager sem = new ScriptEngineManager();

    public static String[] expressions = new String[5];
    static public int expressionCounter = 0;

    public static LocalDate date = LocalDate.now();
    public static LocalTime startTime;
    public static LocalTime endTime;


    /**
     * --------------------------------------------------------------------------------
     * Method for getting current time
     * --------------------------------------------------------------------------------
     */
    public static LocalTime getTime() {
        return LocalTime.now();
    }


    /**
     * --------------------------------------------------------------------------------
     * Method for calculating time spent on quiz
     * --------------------------------------------------------------------------------
     */
    public static long quizTime(LocalTime startTime, LocalTime endTime) {
        System.out.println("You spent " + ChronoUnit.SECONDS.between(endTime, startTime) + " seconds on completing the quiz");
        return ChronoUnit.SECONDS.between(endTime, startTime);
    }

    /**
     * --------------------------------------------------------------------------------
     * Method for getting the name of the user/player
     * --------------------------------------------------------------------------------
     */
    public static String getName() {
        System.out.println("Please enter your name: ");
        return sc.nextLine();
    }


    /**
     * --------------------------------------------------------------------------------
     * Method for generating numbers with 2 different operators that give %2 = 0, meaning no rest.
     * --------------------------------------------------------------------------------
     */
    public static String generateTwoOpLevel(int level, int nrLimit, String op1, String op2) throws ScriptException {

        System.out.println("--------------- Level " + level + "---------------");
        String result;

        boolean stop = false;
        do {
            nrArray(nrLimit);
            twoOpArray(op1, op2);
            exArray();
            result = printStringArray(exArray);

            //calculating expression result in order to leave only integral results
            ScriptEngine level1 = sem.getEngineByName("JavaScript");
            double doubleResult = parseDouble(String.valueOf(level1.eval(result)));
            if (doubleResult % 2 == 0) {
                System.out.println("Expression: " + printStringArray(exArray));
                stop = true;
                //saving expression
                expressions[expressionCounter] = printStringArray(exArray);
            }
        }
        while (!stop);

        //increasing expression counter to make space for next expression in array
        expressionCounter++;

        //increasing number of levels for calculating final score
        nrOfLevels++;

        return result;

    }

    /**
     * --------------------------------------------------------------------------------
     * Method for running mathematical levels
     * --------------------------------------------------------------------------------
     */
    public static void calculateExpression(String generatedExpression) throws ScriptException {

        ScriptEngine level1 = sem.getEngineByName("JavaScript");
        double doubleResult = parseDouble(String.valueOf(level1.eval(generatedExpression)));

        System.out.println("Correct result: " + doubleResult);
        System.out.println("Insert result: ");

        if (sc.nextInt() == doubleResult) {

            //increasing score for calculating final score
            score++;

            System.out.println("Correct answer");

        } else System.out.println("Wrong answer");
    }


    /**
     * --------------------------------------------------------------------------------
     * Method for calculating final score
     * --------------------------------------------------------------------------------
     */
    public static double score(double score, double nrOfLevels) {

        //calculating score out of 100
        System.out.println("Your score: " + (score / nrOfLevels) * 100);
        return (score / nrOfLevels) * 100;
    }

}
