import java.util.Arrays;
import java.util.List;

class Matrix {
    int[][] matrix;
    Matrix(String matrixAsString) {
        String[] split = matrixAsString.split("\n");
        matrix = Arrays.stream(split).map(q-> Arrays.stream(q.split(" ")).mapToInt(Integer::valueOf).toArray()).toArray(int[][]::new);
    }

    int[] getRow(int rowNumber) {
        return matrix[rowNumber-1];
    }

    int[] getColumn(int columnNumber) {
        return Arrays.stream(matrix).mapToInt(q->q[columnNumber-1]).toArray();
    }
}
