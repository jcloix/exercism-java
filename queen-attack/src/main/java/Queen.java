import java.util.Objects;

class Queen {
    private int row;
    private int column;
    Queen(int row, int column) {
        if(column > 7) {
            throw new IllegalArgumentException("Queen position must have column <= 7.");
        } else if (column < 0) {
            throw new IllegalArgumentException("Queen position must have positive column.");
        }
        if(row > 7) {
            throw new IllegalArgumentException("Queen position must have row <= 7.");
        } else if (row < 0) {
            throw new IllegalArgumentException("Queen position must have positive row.");
        }
        this.row=row;
        this.column=column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Queen queen = (Queen) o;
        return row == queen.row && column == queen.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
