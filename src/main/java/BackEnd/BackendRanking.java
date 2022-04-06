package BackEnd;

import java.util.Random;

public class BackendRanking implements Comparable<BackendRanking> {

    static private Random randomNr = new Random();
    static private int generatedNr;
    private int score;
    private int time;
    private String name;
    private String date;
    private int position;
    private int DBid;
    private String javaid;

    public String getJavaid() {
        return this.javaid;
    }

    public void setJavaid(String name) {
        this.javaid = String.valueOf(randomNr.nextInt(22222222)).concat(name).concat(BackendMain.date.toString());
    }

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

    public BackendRanking(int score, int time, String name, int position, int DBid) {
        this.score = score;
        this.time = time;
        this.name = name;
        this.date = BackendMain.date.toString();
        this.position=position;
        this.DBid=DBid;
    }

    public BackendRanking() {
    }

    @Override
    public int compareTo(BackendRanking rank) {
        return Integer.compare(rank.getScore(), this.getScore());
    }


    @Override
    public String toString() {
        return "position= " + position +
                ", score=" + score +
                ", time=" + time +
                ", name='" + name +
                ", date=" + date;
    }
}
