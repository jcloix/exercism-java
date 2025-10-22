import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Bob {

    Set<String> alphabetSet = Stream.concat(
            IntStream.range('a','z').mapToObj(Character::toString),
            IntStream.range('A','Z').mapToObj(Character::toString)
    ).collect(Collectors.toSet());

    String hey(String input) {
        if(input.isBlank()) {
          return "Fine. Be that way!";
        }
        String clean = input.trim();
        boolean yell = input.toUpperCase().equals(input) && hasLetter(input);
        if(clean.endsWith("?") && yell) {
            return "Calm down, I know what I'm doing!";
        }
        if(clean.endsWith("?")) {
            return "Sure.";
        }
        if(yell) {
            return "Whoa, chill out!";
        }
        return "Whatever.";
    }

    boolean hasLetter(String s) {
        for(Character c : s.toCharArray()) {
            if(Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

}