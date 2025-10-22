import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Matrix {
    final Set<MatrixCoordinate> coordinates = new HashSet<>();

    Matrix(List<List<Integer>> values) {
        IntStream.range(0, values.size()).forEach(x -> {
            List<Integer> row = values.get(x);
            if (row.isEmpty()) return;

            // Find the max value in the row
            int max = row.stream().max(Integer::compareTo).get();

            // Find all column indices (y) that match the max
            List<Integer> maxYs = IntStream.range(0, row.size())
                    .filter(y -> row.get(y) == max)
                    .boxed()
                    .toList();
            maxYs.forEach(y-> {
                for(int i=0;i < values.size(); i++) {
                    if(i != x && values.get(i).get(y) < max) return;
                }
                coordinates.add(new MatrixCoordinate(x+1,y+1));
            });
        });
    }

    Set<MatrixCoordinate> getSaddlePoints() {
        return coordinates;
    }
}
