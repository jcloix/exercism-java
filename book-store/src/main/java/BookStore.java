import java.util.*;

class BookStore {
    private static int BOOK_PRICE = 8;
    private static final double[] DISCOUNTS = {
            0.0,    // 1 book, no discount
            0.05,   // 2 books
            0.10,   // 3 books
            0.20,   // 4 books
            0.25    // 5 books
    };

    double calculateBasketCost(List<Integer> books) {
        int[] counts = new int[5];
        for (int book : books) {
            counts[book-1]++;
        }
        return bestPrice(counts, new HashMap<>(), new ArrayList<>());
    }

    double bestPrice(int[] counts, Map<String, Double> memo, List<String> chosen) {
        String key = Arrays.toString(counts);
        if (memo.containsKey(key)) return memo.get(key);

        int total = 0;
        for (int c : counts) total += c;
        if (total == 0) {
            System.out.println(String.join(",",chosen));
            memo.put(key, 0.0);
            return 0.0;
        }

        double min = Double.POSITIVE_INFINITY;
        for(int i = counts.length; i > 0 ; i--) {
            int[] newCounts = Arrays.copyOf(counts, 5);
            boolean isFeasible = pickBooks(newCounts,i);
            if(!isFeasible) continue;
            double setCost = i * BOOK_PRICE * (1.0 - DISCOUNTS[i-1]);
            List<String> newChosen = new ArrayList<>(chosen);
            newChosen.add(""+i);
            double totalCost = setCost + bestPrice(newCounts, memo,newChosen);
            if (totalCost < min) min = totalCost;
        }
        memo.put(key, min);
        return min;
    }

    boolean pickBooks(int[] counts, int nbPick) {
        // Max-heap: largest counts first
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        // load all non-zero counts
        for (int c : counts) {
            if (c > 0) pq.add(c);
        }

        if (pq.size() < nbPick) {
            return false; // not enough distinct books
        }

        // pick nbPick different books
        List<Integer> picked = new ArrayList<>();
        for (int i = 0; i < nbPick; i++) {
            picked.add(pq.poll() - 1); // use one copy of this book
        }

        // put the remaining counts back into counts[]
        Arrays.fill(counts, 0);
        for (int val : pq) {
            counts[counts.length - pq.size()] = val; // placeholder
        }
        for (int val : picked) {
            if (val > 0) pq.add(val);
        }

        // rebuild counts[] from priority queue
        int idx = 0;
        for (int val : pq) {
            counts[idx++] = val;
        }
        while (idx < counts.length) {
            counts[idx++] = 0;
        }

        return true;
    }



}