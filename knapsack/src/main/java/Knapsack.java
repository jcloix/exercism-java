import java.util.*;

class Knapsack {

    int maximumValue(int maximumWeight, List<Item> items) {
        return solutionMyMemo(items,0,maximumWeight,new HashMap<>());
    }

    public int solutionMyDp(int maxWeight,List<Item> items) {
        int[] dp = new int[maxWeight+1];
        for(Item item : items) {
            for(int w=maxWeight; w >= item.weight; w--) {
                dp[w] = Math.max(dp[w],dp[w-item.weight]+item.value);
            }
        }
        return dp[maxWeight];
    }

    public int solutionMyDp2(int maxWeight,List<Item> items) {
        int n = items.size();
        int[][] dp = new int[n+1][maxWeight+1];

        for(int i=1; i <= n; i++) {
            Item item = items.get(i-1);
            for(int w=0;w <= maxWeight;w++) {
                if(item.weight > w) {
                    dp[i][w] = dp[i-1][w];
                } else {
                    dp[i][w] = Math.max(dp[i-1][w],dp[i-1][w-item.weight]+item.value);
                }
            }
        }
        return dp[n][maxWeight];
    }
    public int solutionMyMemo(List<Item> items, int i, int remainingWeight,Map<String,Integer> memo) {
        if(remainingWeight == 0 || i == items.size()) return 0;
        String key = i+","+remainingWeight;
        if(memo.containsKey(key)) return memo.get(key);
        Item item = items.get(i);
        int result;
        if(item.weight > remainingWeight) {
            result = solutionMyMemo(items,i+1,remainingWeight,memo);
        } else {
            int take = item.value + solutionMyMemo(items,i+1,remainingWeight-item.weight,memo);
            int skip = solutionMyMemo(items,i+1,remainingWeight,memo);
            result = Math.max(take,skip);
        }
        memo.put(key,result);
        return result;
    }


}