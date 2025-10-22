import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RotationalCipher {
    List<Character> upper = IntStream.rangeClosed('A', 'Z')
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());

    List<Character> lower = IntStream.rangeClosed('a', 'z')
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());


    int shiftKey;
    RotationalCipher(int shiftKey) {
        this.shiftKey=shiftKey;
    }

    String rotate(String data) {
        return data.chars()
                .mapToObj(c -> (char) c)
                .map(c -> {
                    if (Character.isLowerCase(c)) {
                        return lower.get((c - 'a' + shiftKey) % 26);
                    } else if (Character.isUpperCase(c)) {
                        return upper.get((c - 'A' + shiftKey) % 26);
                    } else {
                        return c;
                    }
                })
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
