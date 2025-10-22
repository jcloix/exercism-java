import java.util.*;

class School {
    Map<Integer, List<String>> rosterMap = new TreeMap<>();
    Set<String> allStudents = new HashSet<>();
    boolean add(String student, int grade) {
        if(allStudents.contains(student)) {
            // Ignore
            return false;
        }
        allStudents.add(student);
        return rosterMap.computeIfAbsent(grade,q->new ArrayList<>()).add(student);
    }

    List<String> roster() {
        return rosterMap
                .entrySet().stream()
                .flatMap(q->q.getValue().stream().sorted()).toList();


    }

    List<String> grade(int grade) {
        if(!rosterMap.containsKey(grade)) {
            return Collections.emptyList();
        }
        return rosterMap.get(grade).stream().sorted().toList();
    }

}
