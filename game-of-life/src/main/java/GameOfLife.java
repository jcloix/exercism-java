import java.util.stream.IntStream;

class GameOfLife {
    public int[][] tick(int[][] matrix){
        return IntStream.range(0, matrix.length).mapToObj(x->
                IntStream.range(0,matrix[x].length).map(y-> {
                    int nbLiveNeighbours = liveNeighbours(x,y,matrix);
                    if(matrix[x][y]==0) {
                        return nbLiveNeighbours == 3 ? 1:0;
                    }
                    return nbLiveNeighbours == 2 || nbLiveNeighbours == 3 ? 1:0;
                }).toArray()).toArray(int[][]::new);

    }

    public boolean isValid(int x, int y, int[][] matrix) {
        if(x < 0 || y < 0 || x >= matrix.length || y >= matrix[x].length) {
            return false;
        }
        return true;
    }

    public int liveNeighbours(int x, int y, int[][] matrix) {
        return (int) IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(-1, 1)
                        .mapToObj(j -> new int[]{x + i, y + j}))
                .filter(pos -> !(pos[0] == x && pos[1] == y)) // skip the center cell itself
                .filter(pos -> isValid(pos[0], pos[1], matrix))
                .filter(pos -> matrix[pos[0]][pos[1]] == 1)
                .count();
    }
}
