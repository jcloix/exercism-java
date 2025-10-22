import java.util.HashSet;
import java.util.Set;

class SumOfMultiples {
    Set<Integer> multiples = new HashSet<>();

    SumOfMultiples(int number, int[] set) {
        for(int s : set) {
            if(s == 0) continue;
            int i = 1;
            int res = i*s;
            while(res < number) {
                multiples.add(res);
                res= i*s;
                i++;
            }
        }
    }

    int getSum() {
        return this.multiples.stream().mapToInt(Integer::intValue).sum();
    }

}
