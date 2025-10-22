public class BowlingGame {
    private final int[] rolls = new int[21 + 2]; // 21 rolls + 2 bonus
    private int currentRoll = 0;

    public void roll(int pins) {
        if (pins < 0) throw new IllegalStateException("Negative roll is invalid");
        if (pins > 10) throw new IllegalStateException("Pin count exceeds pins on the lane");
        if (isGameOver()) throw new IllegalStateException("Cannot roll after game is over");

        if (currentRoll % 2 == 1 && currentRoll < 18 && rolls[currentRoll - 1] != 10 && rolls[currentRoll - 1] + pins > 10)
            throw new IllegalStateException("Pin count exceeds pins on the lane");

        // 10th frame special validation
        if (currentRoll == 19 && !isStrike(18) && rolls[18] + pins > 10)
            throw new IllegalStateException("Pin count exceeds pins on the lane");
        if (currentRoll == 20 && isStrike(18) && !isStrike(19) && rolls[19] + pins > 10)
            throw new IllegalStateException("Pin count exceeds pins on the lane");

        rolls[currentRoll++] = pins;
    }

    public int score() {
        if (!isGameComplete()) {
            throw new IllegalStateException("Score cannot be taken until the end of the game");
        }

        int score = 0;
        int rollIndex = 0;

        for (int frame = 0; frame < 10; frame++) {
            if (isStrike(rollIndex)) {
                score += 10 + rolls[rollIndex + 1] + rolls[rollIndex + 2];
                rollIndex += 1;
            } else if (isSpare(rollIndex)) {
                score += 10 + rolls[rollIndex + 2];
                rollIndex += 2;
            } else {
                score += rolls[rollIndex] + rolls[rollIndex + 1];
                rollIndex += 2;
            }
        }

        return score;
    }

    private boolean isStrike(int rollIndex) {
        return rolls[rollIndex] == 10;
    }

    private boolean isSpare(int rollIndex) {
        return rolls[rollIndex] + rolls[rollIndex + 1] == 10;
    }

    private boolean isGameComplete() {
        int frame = 0;
        int i = 0;

        while (frame < 9 && i < currentRoll) {
            if (rolls[i] == 10) {
                i += 1;
            } else {
                i += 2;
            }
            frame++;
        }

        if (i >= currentRoll) return false; // 10th frame hasn't started yet

        int rollsLeft = currentRoll - i;

        if (rolls[i] == 10) {
            return rollsLeft >= 3; // strike in 10th → need 2 bonus
        } else if (rolls[i] + rolls[i + 1] == 10) {
            return rollsLeft >= 3; // spare in 10th → need 1 bonus
        } else {
            return rollsLeft >= 2; // open 10th → just two rolls
        }
    }

    private boolean isGameOver() {
        if (currentRoll < 20) return false;
        if (isStrike(18)) return currentRoll >= 21;
        if (isSpare(18)) return currentRoll >= 21;
        return currentRoll >= 20;
    }
}
