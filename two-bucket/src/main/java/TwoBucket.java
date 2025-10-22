import java.util.*;

class TwoBucket {
    private final Result result;

    TwoBucket(int bucketOneCap, int bucketTwoCap, int desiredLiters, String startBucket) {
        if(desiredLiters > bucketTwoCap && desiredLiters > bucketOneCap) {
            throw new UnreachableGoalException();
        }
        State start;
        if ("one".equals(startBucket)) {
            start = new State(bucketOneCap, 0, 1); // fill bucket one first
        } else {
            start = new State(0, bucketTwoCap, 1); // fill bucket two first
        }

        this.result = bfs(start, bucketOneCap, bucketTwoCap, desiredLiters, startBucket);
    }

    private Result bfs(State start, int cap1, int cap2, int goal, String startBucket) {
        Queue<State> q = new ArrayDeque<>();
        q.add(start);
        Set<String> visited = new HashSet<>();

        while (!q.isEmpty()) {
            State s = q.poll();
            String key = s.b1 + "," + s.b2;
            if (!visited.add(key)) continue;

            // Success check: goal can be in either bucket
            if (s.b1 == goal || s.b2 == goal) {
                String goalBucket = (s.b1 == goal) ? "one" : "two";
                int other = (s.b1 == goal) ? s.b2 : s.b1;
                return new Result(s.moves, goalBucket, other);
            }

            // Generate next moves
            List<State> next = new ArrayList<>();

            // Fill actions
            if (s.b1 != cap1) next.add(new State(cap1, s.b2, s.moves + 1));
            if (s.b2 != cap2) next.add(new State(s.b1, cap2, s.moves + 1));

            // Empty actions
            if (s.b1 != 0) next.add(new State(0, s.b2, s.moves + 1));
            if (s.b2 != 0) next.add(new State(s.b1, 0, s.moves + 1));

            // Pour 1 -> 2
            if (s.b1 != 0 && s.b2 != cap2) {
                int pour = Math.min(s.b1, cap2 - s.b2);
                next.add(new State(s.b1 - pour, s.b2 + pour, s.moves + 1));
            }

            // Pour 2 -> 1
            if (s.b2 != 0 && s.b1 != cap1) {
                int pour = Math.min(s.b2, cap1 - s.b1);
                next.add(new State(s.b1 + pour, s.b2 - pour, s.moves + 1));
            }

            // Apply the rule: starting bucket may not be empty while other is full
            for (State ns : next) {
                if ("one".equals(startBucket) && ns.b1 == 0 && ns.b2 == cap2) continue;
                if ("two".equals(startBucket) && ns.b2 == 0 && ns.b1 == cap1) continue;
                q.add(ns);
            }
        }

        throw new UnreachableGoalException("No solution found");
    }

    Result getResult() {
        return result;
    }

    private static class State {
        final int b1, b2, moves;
        State(int b1, int b2, int moves) { this.b1 = b1; this.b2 = b2; this.moves = moves; }
    }
}


