import java.util.Arrays;

class StateOfTicTacToe {

    public static GameState determineState(String[] boardString) {
        char[][] board = Arrays.stream(boardString).map(String::toCharArray).toArray(char[][]::new);
        validateBoardShape(board);

        int xCount = count(board, 'X');
        int oCount = count(board, 'O');

        if (oCount > xCount) {
            throw new IllegalArgumentException("Wrong turn order: O started");
        }
        if(xCount - oCount > 1) {
            throw new IllegalArgumentException("Wrong turn order: X went twice");

        }

        boolean xWins = hasWon(board, 'X');
        boolean oWins = hasWon(board, 'O');

        if (xWins && oWins) {
            throw new IllegalArgumentException("Impossible board: game should have ended after the game was won");
        }

        if (xWins && xCount != oCount + 1) {
            throw new IllegalArgumentException("X cannot win without having one more move than O");
        }

        if (oWins && xCount != oCount) {
            throw new IllegalArgumentException("O cannot win without having equal moves as X");
        }

        if (xWins) return GameState.WIN;
        if (oWins) return GameState.WIN;

        return (xCount + oCount == 9) ? GameState.DRAW : GameState.ONGOING;
    }

    private static void validateBoardShape(char[][] board) {
        if (board.length != 3)
            throw new IllegalArgumentException("Board must be 3x3");

        for (char[] row : board) {
            if (row.length != 3)
                throw new IllegalArgumentException("Board must be 3x3");
        }
    }

    private static int count(char[][] board, char player) {
        int count = 0;
        for (char[] row : board) {
            for (char c : row) {
                if (c == player) count++;
            }
        }
        return count;
    }

    private static boolean hasWon(char[][] board, char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||  // Row
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {  // Column
                return true;
            }
        }

        // Check diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }


}
