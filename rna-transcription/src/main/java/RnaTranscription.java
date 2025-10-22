import java.util.stream.Collectors;

class RnaTranscription {

    String transcribe(String dnaStrand) {
        return dnaStrand.codePoints()
                .map(cp -> switch (Character.toUpperCase((char) cp)) {
                    case 'A' -> 'U';
                    case 'T' -> 'A';
                    case 'C' -> 'G';
                    case 'G' -> 'C';
                    default  -> '?';
                })
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

}
