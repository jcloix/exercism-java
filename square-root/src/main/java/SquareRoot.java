public class SquareRoot {
    public int squareRoot(int radicand) {
        return searchSquare(radicand, 1, radicand);
    }

    private int searchSquare(int target, int left, int right) {
        if (left > right) {
            throw new IllegalArgumentException("Input is not a perfect square");
        }

        int result = getHalfDivide(left, right);
        int square = result * result;

        if (square == target) return result;
        if (square > target) return searchSquare(target, left, result - 1);
        return searchSquare(target, result + 1, right);
    }

    private int getHalfDivide(int left, int right) {
        return left + (right - left) / 2;
    }
}
