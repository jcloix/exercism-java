import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Atbash {
    Map<Character, Character> reverse = Stream.concat(Stream.concat(
                    IntStream.rangeClosed('a', 'z')
                            .mapToObj(i -> Map.entry((char) i, (char) ('z' - (i - 'a')))),
                    IntStream.rangeClosed('A', 'Z')
                            .mapToObj(i -> Map.entry((char) i, (char) ('z' - (i - 'A'))))
            ), IntStream.rangeClosed('1','9').mapToObj(i->Map.entry((char)i,(char)i)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    String encode(String input) {
        String s =input.chars().mapToObj(this::encodeOneChar).collect(Collectors.joining());
        return groupCiphers(s);
    }

    String decode(String input) {
        String s = degroupCiphers(input);
        return s.chars().mapToObj(this::encodeOneChar).collect(Collectors.joining());
    }

    String encodeOneChar(int c) {
        Character n = reverse.get((char)c);
        return n == null ? "":String.valueOf(n);
    }

    String groupCiphers(String s) {
        return IntStream.range(0,s.length()).mapToObj(i-> {
            if(i!=0 && (i)%5==0) return " " + s.charAt(i);
            return ""+s.charAt(i);
        }).collect(Collectors.joining());
    }

    String degroupCiphers(String s) {
        return s.replaceAll(" ","");
    }

}
