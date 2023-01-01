package netty.game;


public record ScorePoint(int x, int y, int role, int score) implements Comparable<ScorePoint>, Point {

    public ScorePoint(int x, int y, int role) {
        this(x, y, role, 0);
    }

    @Override
    public int compareTo(ScorePoint o) {
        return o.score;
    }
}
