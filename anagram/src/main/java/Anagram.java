import java.util.List;
import java.util.stream.Collectors;

class Anagram {
    String word;
    public Anagram(String word) {
        this.word=word;
    }

    public List<String> match(List<String> candidates) {
        return candidates.stream().filter(q->isAnagram(word,q)).toList();
    }

    private boolean isAnagram(String word, String candidate) {
        if(word.equalsIgnoreCase(candidate)) {
            return false;
        }
        String wordSorted = word.toLowerCase().chars()
                .mapToObj(c->String.valueOf((char)c)).sorted().collect(Collectors.joining());
        String candidateSorted = candidate.toLowerCase()
                .chars().mapToObj(c->String.valueOf((char)c)).sorted().collect(Collectors.joining());
        return wordSorted.equals(candidateSorted);
    }

}