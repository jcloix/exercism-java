class DifferenceOfSquaresCalculator {

    int computeSquareOfSumTo(int input) {
        double base = input * (((double) input /2)+0.5);
        return ((int)base)*((int)base);
    }

    int computeSumOfSquaresTo(int input) {
    return ((input*(input+1))*((input*2)+1))/6;
    }

    int computeDifferenceOfSquares(int input) {
        return computeSquareOfSumTo(input) - computeSumOfSquaresTo(input);
    }

}
