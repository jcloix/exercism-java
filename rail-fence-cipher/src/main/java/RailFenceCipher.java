import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class RailFenceCipher {
    List<StringBuilder> words;
    int rows;
    RailFenceCipher(int rows) {
        words = new ArrayList<>(rows);
        for(int i =0; i < rows; i++) {
            words.add(new StringBuilder());
        }
        this.rows=rows;
    }

    String getEncryptedData(String message) {
        ArrayDeque<Character> charQueue = new ArrayDeque<>(message.chars().mapToObj(c->(char)c).toList());
        while(!charQueue.isEmpty()) {
            for(int i = 0; i < rows-1; i++) {
                if(charQueue.isEmpty()) {
                    break;
                }
                words.get(i).append(charQueue.pop());
            }
            for(int i = rows-1; i > 0; i--) {
                if(charQueue.isEmpty()) {
                    break;
                }
                words.get(i).append(charQueue.pop());
            }
        }
        return words.stream().map(StringBuilder::toString).collect(Collectors.joining());
    }

    String getDecryptedData(String message) {
        char[] charArray = splitMessage(message);
        return new String(charArray);
    }

    char[] splitMessage(String message) {

        // Step 1: Create an array of the same length as input, to mark zig-zag pattern
        char[] decrypted = new char[message.length()];
        boolean[] marker = new boolean[message.length()];
        int idx = 0;

        // Step 2: Mark positions for each rail
        for (int railNum = 0; railNum < rows; railNum++) {
            int rail = 0;
            int direction = 1;

            for (int i = 0; i < message.length(); i++) {
                if (rail == railNum) {
                    marker[i] = true;
                }
                rail += direction;
                if (rail == 0 || rail == rows - 1) {
                    direction *= -1;
                }
            }

            // Fill characters in marked positions
            for (int i = 0; i < message.length(); i++) {
                if (marker[i]) {
                    decrypted[i] = message.charAt(idx++);
                }
            }

            // Reset marker array for next rail
            java.util.Arrays.fill(marker, false);
        }
        return decrypted;
    }

}