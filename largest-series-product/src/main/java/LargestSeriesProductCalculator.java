import java.util.ArrayList;
import java.util.List;

class LargestSeriesProductCalculator {
    String inputNumber;
    LargestSeriesProductCalculator(String inputNumber) {
        this.inputNumber=inputNumber;
        validateInput(inputNumber);
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {
        validate(numberOfDigits);
        long max = 0;
        for(int i = numberOfDigits; i <= inputNumber.length(); i++) {
            long product = getProduct(inputNumber.substring(i-numberOfDigits,i));
            if(max < product) {
                max = product;
            }
        }
        return max;
    }

    long getProduct(String s) {
        return s.chars().mapToLong(q->((char)q) - '0').reduce((a,b)->a*b).orElse(0L);
    }

    void validateInput(String inputNumber) {
        if(!inputNumber.isBlank() && !isNumber(inputNumber)) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }
    }

    void validate(int numberOfDigits) {
        if(numberOfDigits < 1) {
            throw new IllegalArgumentException("Series length must be non-negative.");
        }
        if(inputNumber.length() < numberOfDigits) {
            throw new IllegalArgumentException("Series length must be less than or equal to the length of the string to search.");
        }
        if(!isNumber(inputNumber)) {
            throw new IllegalArgumentException("String to search may only contain digits.");
        }
    }

    public boolean isNumber(String str) {
        if (str == null || str.isBlank()) return false;
        try {
            Double.parseDouble(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
