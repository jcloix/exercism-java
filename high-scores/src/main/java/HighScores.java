import java.util.Comparator;
import java.util.List;

class HighScores {
    List<Integer> highScores;
    List<Integer> sortedHighScore;
    public HighScores(List<Integer> highScores) {
        this.highScores = highScores;
        this.sortedHighScore = highScores.stream().sorted(Comparator.reverseOrder()).toList();
    }

    List<Integer> scores() {
        return this.highScores;
    }

    Integer latest() {
        return this.highScores.getLast();
    }

    Integer personalBest() {
        return sortedHighScore.getFirst();
    }

    List<Integer> personalTopThree() {
        return sortedHighScore.stream().limit(3).toList();
    }

}
