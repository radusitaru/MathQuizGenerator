package Servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Quiz")
public class Quiz extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
    }

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables
     * --------------------------------------------------------------------------------
     */

    //1.1 Primitive variables
    private double fixedResult;
    private double resultMin;
    private double resultMax;
    private int highestNumber;
    private int numberOfExpressions;
    private int numbersInExpression;


    //1.2 String variables
    private String resultType;
    private String quizName;
    private String quizType;
    private String parameters;
    private String quizId;
    private String quizDate;
    private String quizResultsAndExpressions;


    //1.3 Lists
    List<String> operatorsList = new ArrayList<>();


    /**
     * --------------------------------------------------------------------------------
     * 2. Constructors
     * --------------------------------------------------------------------------------
     */

    //2.1 Quiz type: randomQuiz
    public Quiz(String quizName, List<String> operatorsList, int highestNumber, int numbersInExpression, int numberOfExpressions, String resultType) {
        setQuizName(quizName);
        setOperatorsList(operatorsList);
        setHighestNumber(highestNumber);
        setNumbersInExpression(numbersInExpression);
        setNumberOfExpressions(numberOfExpressions);
        setResultType(resultType);
        setQuizType("randomQuiz");
        setQuizId();
        setQuizDate(Generator.date.toString());
        setParameters(operatorsList.toString()+highestNumber+numbersInExpression+numberOfExpressions+resultType+getQuizType());
    }

    //2.2 Quiz type: fixedResultQuiz - double
    public Quiz(String quizName,List<String> operatorsList, int highestNumber, int numbersInExpression, int numberOfExpressions, double fixedResult) {
        setQuizName(quizName);
        setOperatorsList(operatorsList);
        setHighestNumber((highestNumber));
        setNumbersInExpression(numbersInExpression);
        setNumberOfExpressions(numberOfExpressions);
        setFixedResult(fixedResult);
        setQuizType("fixedResultQuiz");
        setQuizDate(Generator.date.toString());
        setParameters(operatorsList.toString()+highestNumber+numbersInExpression+numberOfExpressions+fixedResult+getQuizType());
        setQuizId();
    }


    //2.3 Quiz type: rangeResultQuiz
    public Quiz(String quizName,List<String> operatorsList, int highestNumber, int numbersInExpression, int numberOfExpressions, String resultType, double resultMin, double resultMax) {
        setQuizName(quizName);
        setOperatorsList(operatorsList);
        setHighestNumber((highestNumber));
        setNumbersInExpression(numbersInExpression);
        setNumberOfExpressions(numberOfExpressions);
        setResultType(resultType);
        setResultMin(resultMin);
        setResultMax(resultMax);
        setQuizType("resultRangeQuiz");
        setQuizDate(Generator.date.toString());
        setParameters(
                operatorsList.toString()+highestNumber+numbersInExpression+numberOfExpressions+resultType+getQuizType()+resultMin+resultMax);
        setQuizId();
    }

    //2.4 Default constructor
    public Quiz(){
    }

    /**
     * --------------------------------------------------------------------------------
     * 3. Setters & getters
     * --------------------------------------------------------------------------------
     */

    public String getParameters() {return parameters;}
    public void setParameters(String parameters) {this.parameters = parameters;}
    public String getQuizType() {return quizType;}
    public void setQuizType(String quizType) {this.quizType = quizType;}
    public String getQuizName() {return quizName;}
    public void setQuizName(String quizName) {this.quizName = quizName;}
    public int getHighestNumber() {return highestNumber;}
    public void setHighestNumber(int highestNumber) {this.highestNumber = highestNumber;}
    public int getNumberOfExpressions() {return numberOfExpressions;}
    public void setNumberOfExpressions(int numberOfExpressions) {this.numberOfExpressions = numberOfExpressions;}
    public String getResultType() {return resultType;}
    public void setResultType(String resultType) {this.resultType = resultType;}
    public double getFixedResult() {return fixedResult;}
    public void setFixedResult(double fixedResult) {this.fixedResult = fixedResult;}
    public double getResultMin() {return resultMin;}
    public void setResultMin(double resultMin) {this.resultMin = resultMin;}
    public double getResultMax() {return resultMax;}
    public void setResultMax(double resultMax) {this.resultMax = resultMax;}
    public String getQuizId() {return quizId;}
    public void setQuizId() {this.quizId =
            quizName.concat(quizType)
            .concat(Generator.date.toString()).concat(String.valueOf(numberOfExpressions)).concat(String.valueOf(highestNumber))
            .concat(String.valueOf(Generator.randomGenerator.nextInt(10000)));}
    public void setQuizId(String id){
        this.quizId=id;
    }
    public void setOperatorsList(List<String> operatorsList) {this.operatorsList = operatorsList;}
    public int getNumbersInExpression() {return numbersInExpression;}
    public void setNumbersInExpression(int numbersInExpression) {this.numbersInExpression = numbersInExpression;}
    public String getQuizDate() {return quizDate;}
    public void setQuizDate(String quizDate) {this.quizDate = quizDate;}
    public String getQuizResultsAndExpressions() {return quizResultsAndExpressions;}
    public void setQuizResultsAndExpressions() {this.quizResultsAndExpressions = String.valueOf(Generator.allExpressionsAndResults);}
    public void setQuizResultsAndExpressions(String expressionAndResults) {this.quizResultsAndExpressions = expressionAndResults;}
}
