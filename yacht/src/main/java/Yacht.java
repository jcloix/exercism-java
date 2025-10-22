import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

class Yacht {
    Map<YachtCategory, Function<int[],Integer>> scoreCalculatorMap = Map.ofEntries(
            Map.entry(YachtCategory.ONES,l-> simpleScore(l,1)),
            Map.entry(YachtCategory.TWOS,l-> simpleScore(l,2)),
            Map.entry(YachtCategory.THREES,l-> simpleScore(l,3)),
            Map.entry(YachtCategory.FOURS,l-> simpleScore(l,4)),
            Map.entry(YachtCategory.FIVES,l-> simpleScore(l,5)),
            Map.entry(YachtCategory.SIXES,l-> simpleScore(l,6)),
            Map.entry(YachtCategory.FULL_HOUSE,l->{
                boolean has3 = false;
                boolean has2 = false;
                Map<Integer,Long> map = Arrays.stream(l).boxed().collect(Collectors.groupingBy(n->n,Collectors.counting()));
                for(Map.Entry<Integer,Long> entry : map.entrySet()) {
                    if(entry.getValue() == 3) {
                        has3=true;
                    }
                    if(entry.getValue() == 2) {
                        has2=true;
                    }
                    if(has3 && has2) {
                        return Arrays.stream(l).sum();
                    }
                }
                return 0;
            }),
            Map.entry(YachtCategory.FOUR_OF_A_KIND,l->{
                Map<Integer,Long> map = Arrays.stream(l).boxed().collect(Collectors.groupingBy(n->n,Collectors.counting()));
                for(Map.Entry<Integer,Long> entry : map.entrySet()) {
                    if(entry.getValue() >= 4) {
                        return entry.getKey()*4;
                    }
                }
                return 0;
            }),
            Map.entry(YachtCategory.LITTLE_STRAIGHT,l->{
                Set<Integer> littleStraight = Set.of(1, 2, 3, 4, 5);
                return Arrays.stream(l).boxed().collect(Collectors.toSet()).equals(littleStraight) ? 30 : 0;
            }),
            Map.entry(YachtCategory.BIG_STRAIGHT,l->{
                Set<Integer> bigStraight = Set.of(2, 3, 4, 5, 6);
                return Arrays.stream(l).boxed().collect(Collectors.toSet()).equals(bigStraight) ? 30 : 0;
            }),
            Map.entry(YachtCategory.CHOICE,l-> Arrays.stream(l).sum()),
            Map.entry(YachtCategory.YACHT,l->{
                int nbDistinct = Arrays.stream(l).distinct().toArray().length;
                return nbDistinct == 1 ? 50:0;
            })
    );
    int score;
    Yacht(int[] dice, YachtCategory yachtCategory) {
        this.score = scoreCalculatorMap.get(yachtCategory).apply(dice);
    }

    int score() {
        return this.score;
    }

    private static int simpleScore(int[] dice,int die) {
        return Arrays.stream(dice).filter(n->n==die).sum();
    }

}
