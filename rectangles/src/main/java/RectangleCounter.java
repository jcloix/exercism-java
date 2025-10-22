import java.util.Arrays;

class RectangleCounter {

    int countRectangles(String[] grid) {
        int nbRectangle=0;
        char[][] gridChar = Arrays.stream(grid)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        for(int i = 0; i < grid.length; i++) {
            for(int j =0; j < grid[i].length(); j++) {
                if(grid[i].charAt(j) == '+') {
                    // Found the top-left corner.
                    nbRectangle+= nbRectangle(i,j,gridChar);
                }
            }
        }
        return nbRectangle;
    }

    int nbRectangle(int startI, int startJ, char[][] grid) {
        int nbRectangle = 0;
        int i = startI +1;
        while(i < grid.length && (grid[i][startJ] == '+' || grid[i][startJ] == '|')) {
            int j = startJ +1;
            if(grid[i][startJ] == '+') {
                // Found a bottom-right corner, let's find the top-right corner.
                while(j < grid[startI].length && (grid[startI][j] == '+' || grid[startI][j] == '-')) {
                    if(grid[startI][j] == '+') {
                        // Found top-right corners.
                        if(grid[i][j] == '+' && isValidHorizontalLine(i,startJ,j,grid) && isValidVerticalLine(startI,j,i,grid)) {
                            // Found the bottom-right corner and verified the integrity of the lines.
                            nbRectangle++;
                        }
                    }
                    j++;
                }
            }
            i++;
        }
        return nbRectangle;
    }

    boolean isValidHorizontalLine(int i, int startJ,int endJ, char[][] grid) {
        for(int j=startJ+1; j < endJ; j++) {
            if(grid[i][j] != '+' && grid[i][j] != '-') return false;
        }
        return true;
    }

    boolean isValidVerticalLine(int startI, int j, int endI,char[][] grid) {
        for(int i=startI+1; i < endI; i++) {
            if(grid[i][j] != '+' && grid[i][j] != '|') return false;
        }
        return true;
    }

}