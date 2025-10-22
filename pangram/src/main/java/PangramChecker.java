import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PangramChecker {
    private final Set<Character> ALPHABET = IntStream.rangeClosed('a', 'z')
            .mapToObj(c -> (char) c)
            .collect(Collectors.toUnmodifiableSet());
    public boolean isPangram(String input) {
        Set<Character> alphabetTemp = new HashSet<>(ALPHABET);
        boolean result = input.toLowerCase().chars().filter(cp -> alphabetTemp.contains((char) cp))
                .allMatch(cp -> alphabetTemp.remove((char) cp));
        return result && alphabetTemp.isEmpty();
    }

}
