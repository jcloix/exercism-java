import java.util.*;
import java.util.random.RandomGenerator;

public class MazeGenerator {
    private static final char EMPTY_CELL = ' ';
    int[][] directions = {
            {-1, 0}, // up
            {0, 1},  // right
            {1, 0},  // down
            {0, -1}  // left
    };

    class Cell {
        boolean visited = false;
        boolean[] walls = {true, true, true, true}; // top, right, bottom, left
    }
    private class Maze {
        public int rowSize;
        public int columnSize;
        public char[][] maze;
        public Maze(int rowSize,int columnSize, char[][] maze){
            this.rowSize=rowSize;
            this.columnSize = columnSize;
            this.maze = maze;
        };
    }

    private static Random RANDOM = new Random();

    public void setSeed(int seed) {
         RANDOM = new Random(seed);
    }

    public char[][] generatePerfectMaze(int rows, int columns) {
        return generatePerfectMaze(rows,columns, RandomGenerator.getDefault().nextInt(8));
    }

    public char[][] generatePerfectMaze(int rows, int columns, int seed) {
        if(rows < 5 || rows > 100 || columns < 5 || columns > 100) {
            throw new IllegalArgumentException("The rows or columns provided is either too big or too low");
        }
        setSeed(seed);
        List<Integer> dirOrder = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(dirOrder,RANDOM);
        Cell[][] maze = initialiseMaze(rows,columns);
        int[] randomPoint = getRandomPosition(rows,columns);
        generateMaze(maze,randomPoint[0],randomPoint[1],rows,columns,new HashSet<>());
        char[][] result =  stringifyMaze(maze,rows,columns);
        int entrance = RANDOM.nextInt(rows);
        int exit = RANDOM.nextInt(rows);
        result[entrance*2+1][0] = '⇨';
        result[exit*2+1][columns*2] = '⇨';
        printGrid(result);
        return result;
    }

    public void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println(); // move to next line after each row
        }
    }

    public Cell[][] initialiseMaze(int rows, int columns) {
        Cell[][] result = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns;j++) {
                result[i][j] = new Cell();
            }
        }
        return result;
    }

    public char[][] stringifyMaze(Cell[][] maze,int rows, int columns) {
        final int rowSize = rows*2+1;
        final int columnSize = columns*2+1;
        char[][] result = new char[rowSize][columnSize];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns;j++) {
                processMazeCell(i,j,maze,result);
            }
        }
        return result;
    }


    public int[] getRandomPosition(int rowCount, int colCount) {
        int index = RANDOM.nextInt(rowCount * colCount);
        int row = index / colCount;
        int col = index % colCount;
        return new int[] { row, col };
    }

    void generateMaze(Cell[][] maze, int x, int y,int rows, int columns, Set<String> visitedCells) {
        visitedCells.add(x+","+y);

        List<Integer> dirOrder = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(dirOrder,RANDOM); // randomize directions

        for (int dir : dirOrder) {
            int nx = x + directions[dir][0];
            int ny = y + directions[dir][1];

            if (nx >= 0 && ny >= 0 && nx < rows && ny < columns && !visitedCells.contains(nx+","+ny)) {
                // Remove walls between current and next
                maze[x][y].walls[dir] = false;
                maze[nx][ny].walls[(dir + 2) % 4] = false; // opposite wall

                generateMaze(maze, nx, ny,rows,columns, visitedCells); // recurse
            }
        }
    }

    public void processMazeCell(int x, int y, Cell[][] maze, char[][] result) {
        Cell cell = maze[x][y];
        int nx = x*2;
        int ny = y*2;
        // Process the cell itself;
        result[nx+1][ny+1] = EMPTY_CELL;
        // Process the four sides (simple cases)
        result[nx+1][ny] = cell.walls[3] ? '│':EMPTY_CELL;
        result[nx][ny+1] = cell.walls[0] ? '─':EMPTY_CELL;
        result[nx+1][ny+2] = cell.walls[1] ? '│':EMPTY_CELL;
        result[nx+2][ny+1] = cell.walls[2] ? '─':EMPTY_CELL;
        // Process the coins, need adjacent cell
        Cell left = y-1>0 ? maze[x][y-1]:null;
        Cell top = x-1>0 ? maze[x-1][y]:null;
        Cell right = y+1<maze[0].length ? maze[x][y+1]:null;
        Cell bottom = x+1<maze.length ? maze[x+1][y]:null;
        result[nx][ny] = processMazeCoinCell(top!=null && top.walls[3],cell.walls[0],cell.walls[3],left!=null && left.walls[0]);
        result[nx][ny+2] = processMazeCoinCell(top!=null && top.walls[1],right!=null && right.walls[0],cell.walls[1],cell.walls[0]);
        result[nx+2][ny] = processMazeCoinCell(cell.walls[3],cell.walls[2],bottom!=null && bottom.walls[3],left!=null && left.walls[2]);
        result[nx+2][ny+2] = processMazeCoinCell(cell.walls[1],right!=null && right.walls[2],bottom!=null && bottom.walls[1],cell.walls[2]);
    }

    public char processMazeCoinCell(boolean wallTop, boolean wallRight, boolean wallDown, boolean wallLeft) {
        if(wallTop && wallRight && wallDown && wallLeft) {
            return '┼';
        }
        if(wallTop && wallRight && wallDown && !wallLeft) {
            return '├';
        }
        if(wallTop && wallRight && !wallDown && wallLeft) {
            return '┴';
        }
        if(wallTop && wallRight && !wallDown && !wallLeft) {
            return '└';
        }
        if(wallTop && !wallRight && wallDown && wallLeft) {
            return '┤';
        }
        if(wallTop && !wallRight && wallDown && !wallLeft) {
            return '│';
        }
        if(wallTop && !wallRight && !wallDown && wallLeft) {
            return '┘';
        }
        if(wallTop && !wallRight && !wallDown && !wallLeft) {
            return EMPTY_CELL;
        }

        if(!wallTop && wallRight && wallDown && wallLeft) {
            return '┬';
        }
        if(!wallTop && wallRight && wallDown && !wallLeft) {
            return '┌';
        }
        if(!wallTop && wallRight && !wallDown && wallLeft) {
            return '─';
        }
        if(!wallTop && wallRight && !wallDown && !wallLeft) {
            return EMPTY_CELL;
        }
        if(!wallTop && !wallRight && wallDown && wallLeft) {
            return '┐';
        }
        if(!wallTop && !wallRight && wallDown && !wallLeft) {
            return EMPTY_CELL;
        }
        if(!wallTop && !wallRight && !wallDown && wallLeft) {
            return EMPTY_CELL;
        }
        if(!wallTop && !wallRight && !wallDown && !wallLeft) {
            return EMPTY_CELL;
        }
        return EMPTY_CELL;
    }



}
