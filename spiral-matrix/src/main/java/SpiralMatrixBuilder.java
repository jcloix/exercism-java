import java.util.Map;

class SpiralMatrixBuilder {

    int[][] buildMatrixOfSize(int size) {
        int[][] matrix = new int[size][size];
        int i = 0,j = 0,visited=1;
        Direction direction = Direction.RIGHT;
        while(visited <= (size*size)) {
            matrix[i][j] = visited++;
            int nextI = i + direction.getY();
            int nextJ = j + direction.getX();

            if (!isValidIndexes(nextI, nextJ, matrix)) {
                direction = direction.getNextDirection();
                nextI = i + direction.getY();
                nextJ = j + direction.getX();
            }

            i = nextI;
            j = nextJ;

        }
        return matrix;
    }

    private boolean isValidIndexes(int i, int j, int[][] matrix) {
        if(i < 0 || i >= matrix.length) {
            return false;
        }
        if(j < 0 || j >= matrix.length) {
            return false;
        }
        return matrix[i][j] == 0;
    }


}

enum Direction {
    RIGHT(1,0),DOWN(0,1),LEFT(-1,0),UP(0,-1);
    private final int x;
    private final int y;
    private static final Map<Direction,Direction> NEXT_DIRECTION = Map.ofEntries(
            Map.entry(RIGHT,DOWN),
            Map.entry(DOWN,LEFT),
            Map.entry(LEFT,UP),
            Map.entry(UP,RIGHT)
    );

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getNextDirection() {
        return NEXT_DIRECTION.getOrDefault(this,RIGHT);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
