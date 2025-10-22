import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RunLengthEncoding {


    public String encode(String data) {
        if (data.isEmpty()) return "";

        StringBuilder encoded = new StringBuilder();
        int count = 1;
        char prev = data.charAt(0);

        for (int i = 1; i < data.length(); i++) {
            char curr = data.charAt(i);
            if (curr == prev) {
                count++;
            } else {
                appendEncoded(encoded, count, prev);
                count = 1;
                prev = curr;
            }
        }
        appendEncoded(encoded, count, prev);
        return encoded.toString();
    }

    private void appendEncoded(StringBuilder sb, int count, char c) {
        if (count > 1) sb.append(count);
        sb.append(c);
    }

    String decode(String data) {
        List<Integer> lettersIndexes =
                IntStream.range(0,data.length())
                        .filter(i->!Character.isDigit(data.charAt(i)))
                        .boxed().toList();
        return IntStream.range(0,lettersIndexes.size()).mapToObj(i-> {
            int currIndex = lettersIndexes.get(i);
            String letter = data.substring(currIndex,currIndex+1);
            int prevIndex = i==0 ? -1:lettersIndexes.get(i-1);
            Integer repeatLetter = (prevIndex >= currIndex-1) ? 1:Integer.valueOf(data.substring(prevIndex+1,currIndex));
            return letter.repeat(repeatLetter);
        }).collect(Collectors.joining());
    }

}