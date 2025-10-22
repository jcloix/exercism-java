import java.util.ArrayList;
import java.util.List;

public class Say {

    private static final String[] BELOW_20 = {
            "", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"
    };

    private static final String[] THOUSANDS = {
            "", "thousand", "million", "billion"
    };

    public String say(long number) {
        if(number < 0) {
            throw new IllegalArgumentException("Negative not allowed");
        }
        if(number >= 1000000000000L) {
            throw new IllegalArgumentException("Too big");
        }
        return spell(number);
    }

    public String spell(long num) {
        if (num == 0) return "zero";

        StringBuilder result = new StringBuilder();
        int group = 0;

        while (num > 0) {
            int chunk = (int) (num % 1000);
            if (chunk != 0) {
                String chunkText = spellBelowThousand(chunk);
                if (!chunkText.isEmpty()) {
                    if (result.length() > 0) result.insert(0, " ");
                    result.insert(0, THOUSANDS[group]);
                    if (!THOUSANDS[group].isEmpty()) result.insert(0, " ");
                    result.insert(0, chunkText);
                }
            }
            num /= 1000;
            group++;
        }

        return result.toString().trim();
    }

    private String spellBelowThousand(int num) {
        StringBuilder sb = new StringBuilder();

        if (num >= 100) {
            sb.append(BELOW_20[num / 100]).append(" hundred");
            num %= 100;
            if (num > 0) sb.append(" ");
        }

        if (num >= 20) {
            sb.append(TENS[num / 10]);
            num %= 10;
            if (num > 0) sb.append("-").append(BELOW_20[num]);
        } else if (num > 0) {
            sb.append(BELOW_20[num]);
        }

        return sb.toString();
    }

}
