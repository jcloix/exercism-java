import java.util.*;
import java.util.stream.IntStream;

class OpticalCharacterReader {
    public static final Map<String, String> NUMBERS_MAP;
    static {
        List<List<String>> digits = List.of(
                List.of(" _ ",
                        "| |",
                        "|_|"), // 0
                List.of("   ",
                        "  |",
                        "  |"), // 1
                List.of(" _ ",
                        " _|",
                        "|_ "), // 2
                List.of(" _ ",
                        " _|",
                        " _|"), // 3
                List.of("   ",
                        "|_|",
                        "  |"), // 4
                List.of(" _ ",
                        "|_ ",
                        " _|"), // 5
                List.of(" _ ",
                        "|_ ",
                        "|_|"), // 6
                List.of(" _ ",
                        "  |",
                        "  |"), // 7
                List.of(" _ ",
                        "|_|",
                        "|_|"), // 8
                List.of(" _ ",
                        "|_|",
                        " _|")  // 9
        );

        Map<String, String> tempMap = new HashMap<>();
        for (int i = 0; i < digits.size(); i++) {
            String key = String.join("", digits.get(i));
            tempMap.put(key, String.valueOf(i));
        }
        NUMBERS_MAP = Collections.unmodifiableMap(tempMap); // Makes it truly constant
    }

    String parse(List<String> input) {
        validateInput(input);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.size(); i += 4) {
            List<String> block = input.subList(i, i + 4);
            result.append(parseBlock(block)).append(",");
        }

        // Remove trailing comma
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }

    private void validateInput(List<String> input) {
        if (input.size() % 4 != 0) {
            throw new IllegalArgumentException("Number of input rows must be a positive multiple of 4");
        }

        for (String line : input) {
            if (line.length() % 3 != 0) {
                throw new IllegalArgumentException("Number of input columns must be a positive multiple of 3");
            }
        }
    }

    private String parseBlock(List<String> block) {
        int digits = block.getFirst().length() / 3;
        StringBuilder parsed = new StringBuilder();

        for (int i = 0; i < digits; i++) {
            String ocrDigit = extractDigit(block, i);
            parsed.append(NUMBERS_MAP.getOrDefault(ocrDigit, "?"));
        }

        return parsed.toString();
    }

    private String extractDigit(List<String> block, int index) {
        StringBuilder digitBuilder = new StringBuilder();

        for (int i = 0; i < 3; i++) { // Only use first 3 rows
            int start = index * 3;
            int end = start + 3;
            digitBuilder.append(block.get(i), start, end);
        }

        return digitBuilder.toString();
    }

}