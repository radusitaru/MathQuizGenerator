package Backend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Play {

    /**
     * --------------------------------------------------------------------------------
     * 1. Variables & lists
     * --------------------------------------------------------------------------------
     */

    //1.1 Primitive variables
    private int score;
    private int time;
    private int position;
    private int DBid; //ID of row in DB

    //1.2 String variables
    private String name;
    private String date;
    private String javaid; //This variable is used for extracting specificic information from database
    private String quizType;
    static public String myRanking; //Position of current quiz in the ranking

    //1.3 Lists
    static public List<Play> scoresFromDB = new ArrayList<>();

    /**
     * --------------------------------------------------------------------------------
     * 2. Constructors
     * --------------------------------------------------------------------------------
     *
     */

    //2.1 Default constructor
    public Play() {
    }

    //2.2 Custom constructor
    public Play(int score, int time, String name, int position, int DBid, String quizType) {
        this.score = score;
        this.time = time;
        this.name = name;
        this.date = Engine.date.toString();
        this.position=position;
        this.DBid=DBid;
        this.quizType=quizType;
    }

    /**
     * --------------------------------------------------------------------------------
     * 3. Methods
     * --------------------------------------------------------------------------------
     */

    //3.1 toString override method
    @Override
    public String toString() {
        return "position= " + position +
                ", score=" + score +
                ", time=" + time +
                ", name='" + name +
                ", date=" + date +
                ", quiz type=" + quizType;
    }

    /**
     * --------------------------------------------------------------------------------
     * 4. Setters & getters
     * --------------------------------------------------------------------------------
     */

    //4.1 Getters & Setters
    public String getJavaid() {
        return this.javaid;
    }
    public void setJavaid(String name) {this.javaid = String.valueOf(Engine.randomGenerator.nextInt(22222222)).concat(name).concat(Engine.date.toString());}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getDBid() {
        return DBid;
    }
    public void setDBid(int DBid) {
        this.DBid = DBid;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static String getMyRanking() {return myRanking;}
    public static void setMyRanking(String myRanking) {Database.myRanking = myRanking;}
    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
}
