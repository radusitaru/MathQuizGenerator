package Backend;

import java.util.List;

public class Quiz {


    /**
     * --------------------------------------------------------------------------------
     * 1. Variables
     * --------------------------------------------------------------------------------
     */

    //1.1 Declaring primitive variables
    private int fixedIntResult;
    private double fixedDoubleResult;
    private double resultMin;
    private double resultMax;
    private int highestNumber;
    private int numberOfExpressions;
    private boolean repeatIntake;

    //1.2 Declaring String variables
    private String resultType;
    private String quizName;
    private String tempErrorMessage;
    private String finalErrorMessage;
    private String quizType;
    private String parameters;

    /**
     * --------------------------------------------------------------------------------
     * 2. Constructors
     * --------------------------------------------------------------------------------
     */

    //2.1 Quiz type: "name"
    public Quiz(String quizName) {
        setQuizName(quizName);
        setQuizType("name");
    }

    //2.2 Quiz type: "name+ResultType"
    Quiz(String quizName, String type) {
        setQuizName(quizName);
        setResultType(type);
        setQuizType("name+ResultType");
        setParameters(type);
    }

    //2.3 Quiz type: "name+resultInt"
    Quiz(String quizName, int result) {
        setQuizName(quizName);
        setFixedIntResult(result);
        setQuizType("name+resultInt");
        setParameters(String.valueOf(result));
    }

    //2.4 Quiz type: "name+resultDouble"
    Quiz(String quizName, double result) {
        setQuizName(quizName);
        setFixedDoubleResult(result);
        setQuizType("name+resultDouble");
        setParameters(String.valueOf(result));
    }

    //2.5 Quiz type: "name+resultRange"
    Quiz(String quizName, double resultMin, double resultMax) {
        setQuizName(quizName);
        setResultMin(resultMin);
        setResultMax(resultMax);
        setQuizType("name+resultRange");
        setParameters(String.valueOf(resultMin + resultMax));
    }

    //2.6 Quiz type: "name+ResultType+resultRange"
    Quiz(String quizName, String type, double resultMin, double resultMax) {
        setQuizName(quizName);
        setResultType(resultType);
        setResultMin(resultMin);
        setResultMax(resultMax);
        setQuizType("name+ResultType+resultRange");
        setParameters(type + resultMin + resultMax);
    }

    //2.7 Constructor for collecting input
    public Quiz(List<String> userInput) {
        //2.7.1 Exceptions that will automatically make user re-introduce intake

        //2.7.1.2 No name of quiz inserted
        if(userInput.get(0)==null);setRepeatIntake(true);

        //2.7.1.3 Inserted fixed int & fixed double result
        if(userInput.get(4)==null && userInput.get(5)!=null);setRepeatIntake(true);

        //2.7.1.4 Inserted fixed result & range result
        if(userInput.get(0)==null);setQuizName(userInput.get(0));
        if(userInput.get(1)!=null);setHighestNumber(Integer.parseInt(userInput.get(1)));
        if(userInput.get(2)!=null);setNumberOfExpressions(Integer.parseInt(userInput.get(2)));
        if(userInput.get(3)!=null);setResultType(userInput.get(3));
        if(userInput.get(4)!=null);setFixedIntResult(Integer.parseInt(userInput.get(4)));
        if(userInput.get(5)!=null);setFixedDoubleResult(Double.parseDouble(userInput.get(5)));
        if(userInput.get(6)!=null);setResultMin(Integer.parseInt(userInput.get(6)));
        if(userInput.get(7)!=null);setResultMax(Integer.parseInt(userInput.get(7)));

    }


    /**
     * --------------------------------------------------------------------------------
     * 3. Methods
     * --------------------------------------------------------------------------------
     */

    //3.1 Comparing quizzes
    public boolean compareQuizzes(Quiz DBquiz) {
        boolean quizExists = false;
        String comparisonDescription = "";

        //3.1.1 Checking if quizzes have the same name
        if (this.getQuizName().equalsIgnoreCase(DBquiz.getQuizName())) {
            comparisonDescription = "Quizes have same name";
            quizExists = true;
        }

        //3.1.2 Checking if a quizzes are the same type
        if (this.quizType.equalsIgnoreCase(DBquiz.quizType)) {
            comparisonDescription = comparisonDescription + " ,quizes are of the same type";

            //3.1.3 Checking if quizzes have the same parameters
            if (this.getParameters().equalsIgnoreCase(DBquiz.getParameters())) {
                comparisonDescription = comparisonDescription + " and parameteres are identical";
                quizExists = true;
            }

            //3.1.3.1 Else for 4.1.3
            else comparisonDescription = comparisonDescription + ", but parameteres are not identical";
        }

        //3.1.2.1 Else for 4.1.2
        else {
            comparisonDescription = "Quiz type not the same";
        }

        System.out.println(comparisonDescription);
        if (quizExists) System.out.println("This quiz exists in database");

        return quizExists;
    }

    /**
     * --------------------------------------------------------------------------------
     * 4. Setters & getters
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
    public int getFixedIntResult() {return fixedIntResult;}
    public void setFixedIntResult(int fixedIntResult) {this.fixedIntResult = fixedIntResult;}
    public double getFixedDoubleResult() {return fixedDoubleResult;}
    public void setFixedDoubleResult(double fixedDoubleResult) {this.fixedDoubleResult = fixedDoubleResult;}
    public double getResultMin() {return resultMin;}
    public void setResultMin(double resultMin) {this.resultMin = resultMin;}
    public double getResultMax() {return resultMax;}
    public void setResultMax(double resultMax) {this.resultMax = resultMax;}
    public String getTempErrorMessage() {return tempErrorMessage;}
    public void setTempErrorMessage(String tempErrorMessage) {this.tempErrorMessage = tempErrorMessage;}
    public String getFinalErrorMessage() {return finalErrorMessage;}
    public void setFinalErrorMessage(String finalErrorMessage) {this.finalErrorMessage = finalErrorMessage;}
    public boolean isRepeatIntake() {return repeatIntake;}
    public void setRepeatIntake(boolean repeatIntake) {this.repeatIntake = repeatIntake;}
}
