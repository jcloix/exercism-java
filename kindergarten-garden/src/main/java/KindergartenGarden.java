import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class KindergartenGarden {
    List<String> students = Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Eve", "Fred",
            "Ginny", "Harriet", "Ileana", "Joseph", "Kincaid", "Larry"
    );

    Map<String, List<Plant>> result = students.stream()
            .collect(Collectors.toMap(
                    student -> student,
                    student -> new ArrayList<>(),
                    (existing, replacement) -> existing,          // merge function, not needed here
                    LinkedHashMap::new                            // use LinkedHashMap to preserve order
            ));

    Map<Integer, String> mapIndex =
            IntStream.range(0, students.size())
                    .boxed()
                    .collect(Collectors.toMap(
                            i -> i,
                            i -> students.get(i),
                            (existing, replacement) -> existing,
                            LinkedHashMap::new
                    ));

    KindergartenGarden(String garden) {
        String[] split = garden.split("\n");
        int i = 0;
        int j = 0;
        while(j < split[0].length()) {
            List<Plant> plants = result.get(students.get(i++));
            plants.add(Plant.getPlant(split[0].charAt(j)));
            plants.add(Plant.getPlant(split[0].charAt(j+1)));
            plants.add(Plant.getPlant(split[1].charAt(j)));
            plants.add(Plant.getPlant(split[1].charAt(j+1)));
            j+=2;
        }

    }

    List<Plant> getPlantsOfStudent(String student) {
        return result.get(student);
    }

}
