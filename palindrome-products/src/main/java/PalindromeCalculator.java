import java.util.*;

class PalindromeCalculator {

    SortedMap<Long, List<List<Integer>>> getPalindromeProductsWithFactors(int minFactor, int maxFactor) {
        validateInput(minFactor,maxFactor);
        SortedMap<Long,List<List<Integer>>> map = new TreeMap<>();
        for(int i=minFactor; i <= maxFactor;i++) {
            for(int j=i; j <= maxFactor;j++) {
                Long result =(long) i*j;
                if(isPalindrome(result)) {
                    // Cannot resolve constructor ArrayList
                    map.computeIfAbsent(result,q->new ArrayList<>()).add(Arrays.asList(i,j));
                }
            }
        }
        return map;
    }

    public void validateInput(int minFactor, int maxFactor) {
        if(minFactor > maxFactor) {
            throw new IllegalArgumentException("invalid input: min must be <= max");
        }
    }

    public boolean isPalindrome(Long number) {
        String s = String.valueOf(number);
        int i=0;
        int j=s.length()-1;
        while(s.charAt(i) == s.charAt(j) && i < j) {
            i++;
            j--;
        }
        return i >= j;
    }

}