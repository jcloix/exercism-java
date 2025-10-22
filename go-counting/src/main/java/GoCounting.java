import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class GoCounting {
    char[][] board;
    private final static Map<Character,Player> CHAR_PLAYER_MAP = Map.ofEntries(
            Map.entry(' ', Player.NONE),
            Map.entry('B', Player.BLACK),
            Map.entry('W', Player.WHITE)
    );
    GoCounting(String board) {
        this.board= Arrays.stream(board.split("\n")).map(String::toCharArray).toArray(char[][]::new);
    }

    Player getTerritoryOwner(int x, int y) {
        if(board[y][x] != ' ') {
            return Player.NONE;
        }
        Set<Point> playerTerritories = new HashSet<>();
        for(Point p : getTerritory(x,y)) {
            playerTerritories.addAll(getPlayerTerritory(p));
        }
        int black = playerTerritories.stream().mapToInt(p->board[p.y][p.x] == 'B' ?1:0).sum();
        int white = playerTerritories.stream().mapToInt(p->board[p.y][p.x] == 'W' ?1:0).sum();
        if(black > 0 && white == 0) {
            return Player.BLACK;
        } else if (white > 0 && black == 0) {
            return Player.WHITE;
        }
        return Player.NONE;
    }

    Set<Point> getTerritory(int x, int y) {
        if(!isValidPoint(x,y)) {
            throw new IllegalArgumentException("Invalid coordinate");
        } else if(board[y][x] != ' ') {
            return Collections.emptySet();
        }
        Set<Point> visited = new HashSet<>();
        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.add(new Point(x, y));
        while(!queue.isEmpty()) {
            Point currentPoint = queue.pop();
            visited.add(currentPoint);
            for(Point toVisit : getNeighbours(currentPoint)) {
                if(isValidPoint(toVisit) && board[toVisit.y][toVisit.x] == ' ' && !visited.contains(toVisit)) {
                    queue.add(toVisit);
                }
            }
        }
        return visited;
    }


    private Set<Point> getPlayerTerritory(Point point) {
        Set<Point> points = getNeighbours(point);
        Set<Point> results = new HashSet<>();
        for(Point p : points) {
            if(isValidPoint(p) && board[p.y][p.x] != ' ') {
                results.add(p);
            }
        }
        return results;
    }

    private Set<Point> getNeighbours(Point point) {
        return Set.of(
                new Point(point.x-1,point.y),
                new Point(point.x,point.y-1),
                new Point(point.x+1,point.y),
                new Point(point.x,point.y+1)
                );
    }

    private boolean isValidPoint(Point p) {
        return isValidPoint(p.x,p.y);
    }

    private boolean isValidPoint(int x, int y) {
        if(x < 0 || y < 0 || y >= board.length || x >= board[0].length) {
            return false;
        }
        return true;
    }


    Map<Player, Set<Point>> getTerritories() {
        Map<Player,Set<Point>> territories = CHAR_PLAYER_MAP.values().stream()
                .collect(Collectors.toMap(p->p,p->new HashSet<>()));
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == ' ') {
                    territories.get(getTerritoryOwner(j,i)).add(new Point(j,i));
                }
            }
        }
        return territories;
    }
}

