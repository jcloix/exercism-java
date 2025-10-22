import java.util.*;
import java.util.stream.Collectors;

class GottaSnatchEmAll {
    static Set<String> newCollection(List<String> cards) {
        return new HashSet<>(cards);
    }

    static boolean addCard(String card, Set<String> collection) {
        return collection.add(card);
    }

    static boolean canTrade(Set<String> myCollection, Set<String> theirCollection) {
        return !myCollection.containsAll(theirCollection) && !theirCollection.containsAll(myCollection);
    }

    static Set<String> commonCards(List<Set<String>> collections) {
        if(collections == null) return null;
        if(collections.isEmpty()) return Collections.emptySet();
        Iterator<Set<String>> it = collections.iterator();
        Set<String> result = new HashSet<>(it.next());
        while(it.hasNext()){
            Set<String> next = it.next();
            result.retainAll(next);
        }
        return result;
    }

    static Set<String> allCards(List<Set<String>> collections) {
        return collections == null? null:collections.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
