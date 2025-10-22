class QueenAttackCalculator {
    private Queen queen1;
    private Queen queen2;
    QueenAttackCalculator(Queen queen1, Queen queen2) {
        if(queen1 == null || queen2 == null) {
            throw new IllegalArgumentException("You must supply valid positions for both Queens.");
        } else if (queen1.equals(queen2)) {
            throw new IllegalArgumentException("Queens cannot occupy the same position.");
        }
        this.queen1=queen1;
        this.queen2=queen2;
    }

    boolean canQueensAttackOneAnother() {
        if(queen1.getRow() == queen2.getRow()) {
            return true;
        }
        if(queen1.getColumn() == queen2.getColumn()) {
            return true;
        }
        return Math.abs(queen1.getRow()-queen2.getRow()) == Math.abs(queen1.getColumn()-queen2.getColumn());
    }



}