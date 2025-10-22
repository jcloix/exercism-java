import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class WordCount {
    public Map<String, Integer> phrase(String input) {
        return Arrays.stream(input.split("[&@$%^:.!?\t\n ,]"))
                .filter(Predicate.not(s->s.isBlank() || "'".equals(s)))
                .map(s-> {
                    int left = s.charAt(0) == '\'' ? 1:0;
                    int right = s.charAt(s.length()-1) == '\'' ? s.length()-1:s.length();
                    return s.substring(left,right);
                })
                .collect(Collectors.groupingBy(String::toLowerCase,Collectors.summingInt(e -> 1)));
    }
}
