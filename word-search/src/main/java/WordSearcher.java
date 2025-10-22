import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class WordSearcher {

    List<Function<Pair,Pair>> DIRECTIONS = List.of(
            q->new Pair(q.getX()+1,q.getY()),
            q->new Pair(q.getX()-1,q.getY()),
            q->new Pair(q.getX(),q.getY()+1),
            q->new Pair(q.getX(),q.getY()-1),
            q->new Pair(q.getX()+1,q.getY()+1),
            q->new Pair(q.getX()-1,q.getY()-1),
            q->new Pair(q.getX()-1,q.getY()+1),
            q->new Pair(q.getX()+1,q.getY()-1)
            );
    Map<String, Optional<WordLocation>> search(final Set<String> words, final char[][] grid) {
        Map<String, Optional<WordLocation>> result = words.stream().collect(Collectors.toMap(q->q,q->Optional.empty()));
        for(String word : words) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    WordLocation r = findWord(word,grid,i,j);
                    if(r != null) {
                        result.put(word,Optional.of(r));
                    }
                }
            }
        }
        return result;
    }


    WordLocation findWord(String word, char[][] grid, int startI, int startJ) {
        if(grid[startI][startJ] != word.charAt(0)) return null;
        // Check horizontal
        Pair start = new Pair(startJ,startI);
        return DIRECTIONS.stream()
                .map(q->checkWord(word,grid,start,q))
                .filter(Objects::nonNull).findAny().orElse(null);
    }


    WordLocation checkWord(String word, char[][] grid, Pair start, Function<Pair,Pair> nextIndex) {
        Pair current = start;
        int i =0;
        while(isValidPosition(grid,current) && grid[current.getY()][current.getX()] == word.charAt(i)) {
            i++;
            if(i >= word.length()) {
                return new WordLocation(humanIndexPair(start),humanIndexPair(current));
            }
            current = nextIndex.apply(current);
        }
        return null;
    }

    Pair humanIndexPair(Pair p) {
        return new Pair(p.getX()+1,p.getY()+1);
    }

    boolean isValidPosition(char[][] grid, Pair position) {
        if(position.getX() < 0 || position.getY() < 0) return false;
        if(position.getX() >= grid[0].length || position.getY() >= grid.length) return false;
        return true;
    }
}
