import java.util.*;

public class KillerSudokuHelper {

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize, List<Integer> exclude) {
        Set<Integer> excludeSet = new HashSet<>(exclude);
        return solution(cageSum,cageSize,excludeSet);

    }


    List<List<Integer>> solution(Integer cageSum, Integer cageSize, Set<Integer> exclude) {
        Map<Integer,Set<Set<Integer>>> possibilities = new HashMap<>();
        possibilities.computeIfAbsent(cageSum, k -> new HashSet<>()).add(new HashSet<>());
        for(int j = cageSum; j > 0; j--) {
            if(!possibilities.containsKey(j)) continue;
            for (int i = 9; i > 0; i--) {
                for (Set<Integer> combo : possibilities.get(j)) {
                    if (exclude.contains(i) || combo.contains(i) || combo.size() == cageSize) continue;
                    int next = j - i;
                    if (next >= 0) {
                        Set<Integer> nextSet = new HashSet<>(combo);
                        nextSet.add(i);
                        possibilities.computeIfAbsent(next, HashSet::new).add(nextSet);
                    }
                }
            }
        }

        if(!possibilities.containsKey(0)) return new ArrayList<>();

        return possibilities.get(0).stream().filter(q->q.size()==cageSize).map(q->q.stream().toList()).toList();
    }

    List<List<Integer>> combinationsInCage(Integer cageSum, Integer cageSize) {
        return combinationsInCage(cageSum,cageSize,new ArrayList<>());
    }

}
