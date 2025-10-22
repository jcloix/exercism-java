import java.util.Arrays;
import java.util.List;

class FlowerFieldBoard {
    char[][] board;
    FlowerFieldBoard(List<String> boardRows) {
        board = boardRows.stream().map(String::toCharArray).toArray(char[][]::new);
        for(int i = 0; i <board.length; i++) {
            for(int j = 0; j <board[i].length; j++) {
                if(board[i][j] == '*') {
                    applyFlowerField(i,j);
                }
            }
        }
    }

    List<String> withNumbers() {
        return Arrays.stream(board).map(String::valueOf).toList();
    }

    void applyFlowerField(int i, int j) {
        addFlowerHint(i-1,j-1);
        addFlowerHint(i-1,j);
        addFlowerHint(i-1,j+1);
        addFlowerHint(i,j-1);
        addFlowerHint(i,j+1);
        addFlowerHint(i+1,j-1);
        addFlowerHint(i+1,j);
        addFlowerHint(i+1,j+1);
    }

    void addFlowerHint(int i, int j) {
        if(i < 0 || j < 0 || i >= board.length || j >= board[i].length || board[i][j] == '*') {
            return;
        }
        if(board[i][j] == ' ') {
            board[i][j]='1';
            return;
        }
        board[i][j]+=1;
    }

}