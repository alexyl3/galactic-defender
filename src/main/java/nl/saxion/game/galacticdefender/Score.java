package nl.saxion.game.galacticdefender;

public class Score implements Comparable<Score> {
    int score;
    String username;

    @Override
    public int compareTo(Score otherScore) {
        return Integer.compare(score, otherScore.score);
    }
}

