import java.util.*;
import java.util.stream.IntStream;

class ChangeCalculator {
    List<Integer> currencyCoins;
    ChangeCalculator(List<Integer> currencyCoins) {
        this.currencyCoins = currencyCoins;
    }

    List<Integer> computeMostEfficientChange(int grandTotal) {
        return memoization(grandTotal);
    }

    List<Integer> memoization(int grandTotal) {
        if (grandTotal < 0) throw new IllegalArgumentException("Negative totals are not allowed.");
        List<Integer> results = memoizationRecursive(grandTotal,this.currencyCoins, new HashMap<>());
        if(results == null){
            throw new IllegalArgumentException("The total " + grandTotal + " cannot be represented in the given currency.");
        }
        return results;
    }

    List<Integer> memoizationRecursive(int number, List<Integer> currencyCoins, Map<Integer,List<Integer>> memo) {
        if(memo.containsKey(number)) return memo.get(number);
        if(number== 0) return new ArrayList<>();
        if(number < 0) return null;
        List<Integer> shortestComb = null;
        for(Integer curr : currencyCoins){
            int remain = number-curr;
            List<Integer> dp = memoizationRecursive(remain,currencyCoins, memo);
            List<Integer> combination = new ArrayList<>();
            if (dp!=null){
                combination.add(curr);
                combination.addAll(dp);
                if(shortestComb == null || combination.size() < shortestComb.size()) {
                    shortestComb = combination;
                }
            }
        }
        memo.put(number,shortestComb);
        return shortestComb;
    }

    List<Integer> arrReconstruct(int grandTotal) {
        if (grandTotal < 0) throw new IllegalArgumentException("Negative totals are not allowed.");
        int[] dp = new int[grandTotal + 1];
        int[] parent = new int[grandTotal + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // zero coins needed for amount 0
        for (int amount = 1; amount <= grandTotal; amount++) {
            for (int coin : this.currencyCoins) {
                if(coin <= amount && dp[amount-coin] != Integer.MAX_VALUE) {
                    int candidate = dp[amount-coin]+1;
                    if(candidate < dp[amount]) {
                        dp[amount] = candidate;
                        parent[amount] = coin;
                    }
                }
            }
        }

        if (dp[grandTotal] == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("The total " + grandTotal + " cannot be represented in the given currency.");
        }
        List<Integer> result = new ArrayList<>();
        int cur = grandTotal;
        while (cur > 0) {
            result.add(parent[cur]);
            cur-=parent[cur];
        }
        return result;
    }

    List<Integer> computeMostEfficientChangeTabulation(int grandTotal) {
        if(grandTotal < 0) throw new IllegalArgumentException("Negative totals are not allowed.");
        List<List<Integer>> results = new ArrayList<>(grandTotal+1);
        results.add(new ArrayList<>());
        IntStream.range(0,grandTotal).forEach(q->results.add(null));
        for(int i = 0; i < results.size(); i++) {
            for(Integer curr : this.currencyCoins) {
                List<Integer> currPossibilities = results.get(i);
                if(i+curr > grandTotal || currPossibilities == null) continue;
                List<Integer> possibilities = results.get(i+curr);
                List<Integer> newPossibilities = new ArrayList<>(currPossibilities);
                newPossibilities.add(curr);
                if(possibilities== null || newPossibilities.size() < possibilities.size()) {
                    results.set(i+curr,newPossibilities);
                }
            }
        }
        if(results.get(grandTotal) == null) throw new IllegalArgumentException("The total "+grandTotal+" cannot be represented in the given currency.");
        return results.get(grandTotal);
    }

}
