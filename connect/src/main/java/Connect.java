import java.util.*;

class Connect {
    String[] board ;
    public Connect(String[] board) {
        this.board = board;

    }

    public Winner computeWinner() {
        // Check player O
        if(playerOWin()) return Winner.PLAYER_O;
        // Check player X
        if(playerXWin()) return Winner.PLAYER_X;
        return Winner.NONE;
    }

    public boolean playerOWin() {
        Set<Pair<Integer,Integer>> visitedCells = new HashSet<>();
        for(int i=0; i < board[0].length();i++) {
            if(board[0].charAt(i) == 'O') {
                if(playerOWinRecursive(visitedCells,0,i)) return true;
            }
        }
       return false;
    }

    public boolean playerOWinRecursive(Set<Pair<Integer,Integer>> visitedCells, int x, int y) {
        if(x < 0 || x > board.length-1 || y < 0 || y > board[0].length()-1+x) return false;
        if(visitedCells.contains(Pair.of(x,y))) return false;
        visitedCells.add(Pair.of(x,y));
        if(board[x].charAt(y) != 'O') return false;
        // Check if we reached the bottom.
        if(x == board.length-1) return true;
        return playerOWinRecursive(visitedCells,x+1,y+1)
                || playerOWinRecursive(visitedCells,x+1,y-1)
                || playerOWinRecursive(visitedCells,x,y+2)
                || playerOWinRecursive(visitedCells,x,y-2)
                || playerOWinRecursive(visitedCells,x-1,y-1)
                || playerOWinRecursive(visitedCells,x-1,y+1);
    }

    public boolean playerXWin() {
        Set<Pair<Integer,Integer>> visitedCells = new LinkedHashSet<>();
        for(int i=0; i < board.length;i++) {
            if(board[i].charAt(i) == 'X') {
                if(playerXWinRecursive(visitedCells,i,i)) return true;
            }
        }
        return false;
    }

    public boolean playerXWinRecursive(Set<Pair<Integer,Integer>> visitedCells, int x, int y) {
        if(x < 0 || x > board.length-1 || y < 0 || y > board[0].length()-1+x) return false;
        if(visitedCells.contains(Pair.of(x,y))) return false;
        if(board[x].charAt(y) != 'X') return false;
        visitedCells.add(Pair.of(x,y));
        // Check if we reached the right.
        if(y == board[0].length()-1+x) return true;
        return playerXWinRecursive(visitedCells,x+1,y+1)
                || playerXWinRecursive(visitedCells,x+1,y-1)
                || playerXWinRecursive(visitedCells,x,y+2)
                || playerXWinRecursive(visitedCells,x,y-2)
                || playerXWinRecursive(visitedCells,x-1,y-1)
                || playerXWinRecursive(visitedCells,x-1,y+1);
    }

    static class Pair<F, S> {
        public final F first;
        public final S second;

        /**
         * Constructor for a Pair.
         *
         * @param first the first object in the Pair
         * @param second the second object in the pair
         */
        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        /**
         * Checks the two objects for equality by delegating to their respective
         * {@link Object#equals(Object)} methods.
         *
         * @param o the {@link Pair} to which this one is to be checked for equality
         * @return true if the underlying objects of the Pair are both considered
         *         equal
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair<?, ?> p = (Pair<?, ?>) o;
            return Objects.equals(p.first, first) && Objects.equals(p.second, second);
        }

        /**
         * Compute a hash code using the hash codes of the underlying objects
         *
         * @return a hashcode of the Pair
         */
        @Override
        public int hashCode() {
            return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
        }

        /**
         * Convenience method for creating an appropriately typed pair.
         * @param a the first object in the Pair
         * @param b the second object in the pair
         * @return a Pair that is templatized with the types of a and b
         */
        public static <A, B> Pair <A, B> of(A a, B b) {
            return new Pair<>(a, b);
        }
    }
}

