import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Robot {
    Orientation orientation;
    GridPosition position;
    private static final int ORIENTATION_LENGTH = Orientation.values().length;
    private static final Map<Orientation,Orientation> LEFT =
            IntStream.range(0, Orientation.values().length)
                    .mapToObj(i-> Map.entry(
                            Orientation.values()[i],
                            Orientation.values()[(i-1+ORIENTATION_LENGTH) % ORIENTATION_LENGTH]))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    private static final Map<Orientation,Orientation> RIGHT =
            IntStream.range(0, Orientation.values().length)
                    .mapToObj(i-> Map.entry(
                            Orientation.values()[i],
                            Orientation.values()[(i+1+ORIENTATION_LENGTH) % ORIENTATION_LENGTH]))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Robot(GridPosition initialPosition, Orientation initialOrientation) {
        this.position = initialPosition;
        this.orientation = initialOrientation;
    }

    GridPosition getGridPosition() {
        return this.position;
    }

    Orientation getOrientation() {
        return this.orientation;
    }

    void advance() {
        switch(orientation) {
            case NORTH -> this.position=new GridPosition(this.position.x,this.position.y+1);
            case SOUTH -> this.position=new GridPosition(this.position.x,this.position.y-1);
            case WEST -> this.position=new GridPosition(this.position.x-1,this.position.y);
            case EAST -> this.position=new GridPosition(this.position.x+1,this.position.y);
        }
    }

    void turnLeft() {
        this.orientation = LEFT.get(this.orientation);
    }

    void turnRight() {
        this.orientation = RIGHT.get(this.orientation);
    }

    void simulate(String instructions) {
        instructions.chars().mapToObj(c->(char)c).forEach(c->{
            switch (c) {
                case 'A' -> advance();
                case 'L' -> turnLeft();
                case 'R' -> turnRight();
            }
        });
    }



}