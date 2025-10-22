import java.util.Arrays;
import java.util.stream.Collectors;

class Acronym {
    String acronym = "";

    Acronym(String phrase) {

        String[] words = phrase.replaceAll("_","").split("[ -]+");
        acronym = Arrays.stream(words)
                .map(q->String.valueOf(Character.toUpperCase(q.charAt(0))))
                .collect(Collectors.joining());
    }

    String get() {
        return acronym;
    }

}
