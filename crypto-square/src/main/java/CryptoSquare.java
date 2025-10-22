import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
class CryptoSquare {
    private final String cipherText;

    CryptoSquare(String plaintext) {
        String normalized = normalize(plaintext);
        int len = normalized.length();

        if (len == 0) {
            cipherText = "";
            return;
        }

        int c = (int) Math.ceil(Math.sqrt(len));
        int r = (int) Math.floor(Math.sqrt(len));
        if (r * c < len) r++;

        // Fill rows
        String[] rows = new String[r];
        for (int i = 0; i < r; i++) {
            int start = i * c;
            int end = Math.min(start + c, len);
            rows[i] = normalized.substring(start, end);
        }

        // Read columns
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < c; col++) {
            if (col > 0) sb.append(" ");
            for (int row = 0; row < r; row++) {
                if (col < rows[row].length()) {
                    sb.append(rows[row].charAt(col));
                } else {
                    sb.append(" ");
                }
            }
        }

        cipherText = sb.toString();
    }

    String getCiphertext() {
        return cipherText;
    }

    private String normalize(String input) {
        return input.toLowerCase().replaceAll("[^a-z0-9]", "");
    }
}
