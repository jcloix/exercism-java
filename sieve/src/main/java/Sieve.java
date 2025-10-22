import java.util.ArrayList;
import java.util.List;

class Sieve {
    List<Integer> primes = new ArrayList<>();
    Sieve(int maxPrime) {
        if(maxPrime < 1) {
            throw new IllegalArgumentException();
        }
        int i=2;
        while(i <= maxPrime) {
            if(isPrime(i,primes)) {
                primes.add(i);
            }
            i++;
        }
    }

    List<Integer> getPrimes() {
        return primes;
    }

    boolean isPrime(int num, List<Integer> primes) {
        for(Integer prime : primes) {
            if(num % prime == 0) return false;
        }
        return true;
    }
}
