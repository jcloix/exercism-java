import java.util.Random;

public class Cipher {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private String key;
    public Cipher() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(100);

        for (int i = 0; i < 100; i++) {
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }

        this.key = sb.toString();
    }

    public Cipher(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String encode(String plainText) {
        StringBuilder result = new StringBuilder();
        for(int i=0; i < plainText.length(); i++) {
            result.append(getEncodedChar(plainText.charAt(i),i));
        }
        return result.toString();
    }

    public String decode(String cipherText) {
        StringBuilder result = new StringBuilder();
        for(int i=0; i < cipherText.length(); i++) {
            result.append(getDecodedChar(cipherText.charAt(i),i));
        }
        return result.toString();
    }

    Character getEncodedChar(char c, int i) {
        char k = getKeyChar(i);
        int shift = k - 'a';
        return (char) ('a' + ((c - 'a' + shift) % 26 + 26) % 26);
    }

    Character getDecodedChar(char c, int i) {
        char k = getKeyChar(i);
        int shift = (k - 'a') *-1;
        return (char) ('a' + ((c - 'a' + shift) % 26 + 26) % 26);

    }

    Character getKeyChar(int i) {
        int index = i % this.key.length();
        return this.key.charAt(index);
    }
}
