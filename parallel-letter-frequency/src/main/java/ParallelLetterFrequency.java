import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class ParallelLetterFrequency {
    Map<Character,Integer> countLetter;
    ParallelLetterFrequency(String[] texts) {
        countLetter=Arrays.stream(texts).parallel()
                .flatMap(q->q.chars()
                        .parallel()
                        .mapToObj(c->(char)c)
                        .filter(Character::isLetter)
                        .map(Character::toLowerCase))
                .collect(Collectors.groupingByConcurrent(
                        q->q,
                        Collectors.summingInt(e -> 1))
                );
    }

    Map<Character, Integer> countLetters() {
        return countLetter;
    }

}
