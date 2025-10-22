import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DiamondPrinter {

    List<String> printToList(char a) {
        int nb = a - 'A';
        List<String> result = new ArrayList<>();

        // Upper part including middle line
        IntStream.rangeClosed(0, nb)
                .mapToObj(i -> getLine((char) ('A' + i), nb, i))
                .forEach(result::add);

        // Lower part (mirror of above, excluding middle line)
        IntStream.iterate(nb - 1, i -> i >= 0, i -> i - 1)
                .mapToObj(i -> getLine((char) ('A' + i), nb, i))
                .forEach(result::add);

        return result;
    }

    String getLine(char c, int nbTotal, int i) {
        int outerSpaces = nbTotal - i;
        if (i == 0) {
            return " ".repeat(outerSpaces) + c + " ".repeat(outerSpaces);
        } else {
            int innerSpaces = i * 2 - 1;
            return " ".repeat(outerSpaces) + c + " ".repeat(innerSpaces) + c + " ".repeat(outerSpaces);
        }
    }

}
