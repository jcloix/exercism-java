import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

class Scrabble {

    public static final Map<Character, Integer> LETTER_VALUES = Stream.of(
                    new AbstractMap.SimpleEntry<>("AEIOULNRST", 1),
                    new AbstractMap.SimpleEntry<>("DG", 2),
                    new AbstractMap.SimpleEntry<>("BCMP", 3),
                    new AbstractMap.SimpleEntry<>("FHVWY", 4),
                    new AbstractMap.SimpleEntry<>("K", 5),
                    new AbstractMap.SimpleEntry<>("JX", 8),
                    new AbstractMap.SimpleEntry<>("QZ", 10)
            )
            .flatMap(e -> e.getKey().chars().mapToObj(c -> Map.entry((char) c, e.getValue())))
            .collect(java.util.stream.Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    int score;
    Scrabble(String word) {
        score = word.chars().map(q->LETTER_VALUES.getOrDefault(Character.toUpperCase((char)q),0)).sum();
    }

    int getScore() {
        return score;
    }

}
