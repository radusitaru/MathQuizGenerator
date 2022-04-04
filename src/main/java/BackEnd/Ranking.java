package BackEnd;

import java.util.Date;

public class Ranking implements Comparable<Ranking> {

    private int score;
    private int time;
    private String name;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Ranking(int score, int time, String name, Date date) {
        this.score = score;
        this.time = time;
        this.name = name;
        this.date = date;
    }

    public Ranking() {
    }

    @Override
    public int compareTo(Ranking rank) {
        return Integer.compare(rank.getScore(), this.getScore());
    }


    @Override
    public String toString() {
        return "Ranking{" +
                "score=" + score +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
