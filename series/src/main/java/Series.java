import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

class Series {
    String string;
    Series(String string) {
        if(string.isBlank()) {
            throw new IllegalArgumentException("series cannot be empty");
        }
        this.string=string;
    }

    List<String> slices(int num) {
        if(num <=0) {
            throw new IllegalArgumentException("slice length cannot be negative or zero");
        } else if (num > string.length()) {
            throw new IllegalArgumentException("slice length cannot be greater than series length");
        }
        return IntStream.rangeClosed(0,string.length()-num).mapToObj(i->string.substring(i,i+num)).toList();
    }
}
