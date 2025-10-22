import java.util.*;

class RelativeDistance {
    private final Map<String, Set<String>> relationMap = new HashMap<>();

    RelativeDistance(Map<String, List<String>> familyTree) {
        for (Map.Entry<String, List<String>> entry : familyTree.entrySet()) {
            String child = entry.getKey();
            List<String> parents = entry.getValue();

            relationMap.computeIfAbsent(child, k -> new HashSet<>()).addAll(parents);
            for (String parent : parents) {
                relationMap.computeIfAbsent(parent, k -> new HashSet<>()).add(child);
                for (String otherParent : parents) {
                    if (!parent.equals(otherParent)) {
                        relationMap.get(parent).add(otherParent);
                    }
                }
            }
        }
    }

    int degreeOfSeparation(String personA, String personB) {
        if (!relationMap.containsKey(personA) || !relationMap.containsKey(personB)) return -1;
        if (personA.equals(personB)) return 0;
        return bfs(personA, personB);
    }

    private int bfs(String start, String target) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String person = queue.poll();
                for (String neighbor : relationMap.getOrDefault(person, Collections.emptySet())) {
                    if (neighbor.equals(target)) return level;
                    if (visited.add(neighbor)) queue.add(neighbor);
                }
            }
            level++;
        }

        return -1;
    }
}
