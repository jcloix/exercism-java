import java.util.*;
import java.util.stream.LongStream;

class PrimeFactorsCalculator {

    List<Long> calculatePrimeFactorsOf(long number) {
        return of(number);
    }
    public static List<Long> of(long number) {
        List<Long> factors = new ArrayList<>();
        long divisor = 2;

        while (number > 1) {
            while (number % divisor == 0) {
                factors.add(divisor);
                number /= divisor;
            }
            divisor++;
        }

        return factors;
    }

}