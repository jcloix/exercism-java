import java.util.HashSet;
import java.util.Set;

class IsogramChecker {

    boolean isIsogram(String phrase) {
        Set<Integer> set = new HashSet<>();
        String clean = phrase.replaceAll("[- ]","").toLowerCase();
        return clean.chars().allMatch(set::add);
    }

}
