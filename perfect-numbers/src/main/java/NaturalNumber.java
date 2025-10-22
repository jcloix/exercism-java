import java.util.stream.IntStream;

class NaturalNumber {
    Classification classification;
    NaturalNumber(int number) {
        validateInput(number);
        int sumFactors = sumFactors(number);
        classification = sumFactors == number ? Classification.PERFECT: sumFactors > number ? Classification.ABUNDANT:Classification.DEFICIENT;
    }

    Classification getClassification() {
        return classification;
    }

    public int sumFactors(int number) {
        return IntStream.range(1,number).filter(i-> number % i == 0).sum();
    }

    public void validateInput(int number) {
        if(number <= 0) {
            throw new IllegalArgumentException("You must supply a natural number (positive integer)");
        }
    }
}
