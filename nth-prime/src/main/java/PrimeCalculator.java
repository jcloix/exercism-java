import java.util.ArrayList;
import java.util.List;

class PrimeCalculator {

    int nth(int nth) {
        if(nth == 0) {
            throw new IllegalArgumentException();
        }
        List<Integer> primes = new ArrayList<>();
        int i=2;
        while(primes.size() < nth) {
            if(isPrimeNumber(i,primes)) {
                primes.add(i);
            }
            i++;
        }
        return primes.get(nth-1);
    }

    boolean isPrimeNumber(int n,List<Integer> primes) {
        for(Integer prime : primes) {
            if(n%prime == 0) {
                return false;
            }
        }
        return true;
    }

}
