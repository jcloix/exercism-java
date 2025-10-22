import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class NucleotideCounter {
    private static final Set<Character> DISTINCT_NUCLEOTIDE = Set.of('A','C','T','G');
    private final Map<Character,Integer> countCharMap;

    public NucleotideCounter(String sequence) {
        countCharMap = sequence.chars()
                .mapToObj(c -> (char)c)
                .peek(c -> {
                    if (!DISTINCT_NUCLEOTIDE.contains(c)) {
                        throw new IllegalArgumentException("Invalid nucleotide: " + c);
                    }
                })
                .collect(Collectors.groupingBy(c -> c, Collectors.summingInt(e -> 1)));

        // Ensure all four nucleotides are present in the result
        DISTINCT_NUCLEOTIDE.forEach(e -> countCharMap.putIfAbsent(e, 0));
    }

    public Map<Character, Integer> nucleotideCounts() {
        return Map.copyOf(countCharMap);
    }

}