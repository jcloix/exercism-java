import java.util.*;

class RelationshipComputer<T> {

    public Relationship computeRelationship(List<T> listOne, List<T> listTwo) {
        if (listOne.equals(listTwo)) {
            return Relationship.EQUAL;
        }
        if (isSublist(listOne, listTwo)) {
            return Relationship.SUBLIST;
        }
        if (isSublist(listTwo, listOne)) {
            return Relationship.SUPERLIST;
        }
        return Relationship.UNEQUAL;
    }

    private boolean isSublist(List<T> small, List<T> big) {
        int smallSize = small.size();
        int bigSize = big.size();

        if (smallSize == 0) {
            return true; // empty list is sublist of any list
        }
        if (smallSize > bigSize) {
            return false;
        }

        for (int i = 0; i <= bigSize - smallSize; i++) {
            if (big.subList(i, i + smallSize).equals(small)) {
                return true;
            }
        }
        return false;
    }
}
