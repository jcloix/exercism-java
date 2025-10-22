import java.util.*;

class Dominoes {

    List<Domino> formChain(List<Domino> inputDominoes) throws ChainNotFoundException {
        if(inputDominoes.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayDeque<Domino> solution = new ArrayDeque<>();
        boolean foundSolution = formChainRecursive(solution, inputDominoes, null);
        if(!foundSolution) {
            throw new ChainNotFoundException("No domino chain found.");
        }
        return solution.stream().toList();
    }

    boolean formChainRecursive(ArrayDeque<Domino> chainDominoes, List<Domino> inputDominoes, Domino currentDomino) {
        if(inputDominoes.isEmpty()) {
            return chainDominoes.peekLast().getRight() == chainDominoes.peekFirst().getLeft();
        }

        for(Domino domino : inputDominoes) {
            if(currentDomino == null || currentDomino.getRight() == domino.getLeft()) {
                chainDominoes.add(domino);
                List<Domino> copy = new ArrayList<>(List.copyOf(inputDominoes));
                copy.remove(domino);

                if(formChainRecursive(chainDominoes,copy,domino)) {
                    return true;
                }
                chainDominoes.pollLast();
            }
            if(currentDomino == null || currentDomino.getRight() == domino.getRight()) {
                Domino reserveDomino = new Domino(domino.getRight(), domino.getLeft());
                chainDominoes.add(reserveDomino);
                List<Domino> copy = new ArrayList<>(List.copyOf(inputDominoes));
                copy.remove(domino);
                if(formChainRecursive(chainDominoes,copy,reserveDomino)) {
                    return true;
                }
                chainDominoes.pollLast();
            }
        }
        return false;
    }

}