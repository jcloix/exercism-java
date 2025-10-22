import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class VariableLengthQuantity {

    List<String> encode(List<Long> numbers) {
        return numbers.stream()
                .flatMap(n -> encodeOne(n).stream())
                .toList();
    }

    private List<String> encodeOne(long n) {
        List<String> result = new ArrayList<>();

        if (n == 0) {
            result.add("0x0");
            return result;
        }

        List<Byte> chunks = new ArrayList<>();

        // Step 1: Break number into 7-bit chunks (least significant to most)
        while (n > 0) {
            byte chunk = (byte)(n & 0x7F);  // keep only the last 7 bits
            chunks.add(chunk);
            n >>= 7;  // shift right by 7
        }

        // Step 2: Reverse and set continuation bits
        Collections.reverse(chunks);
        for (int i = 0; i < chunks.size(); i++) {
            byte b = chunks.get(i);
            if (i != chunks.size() - 1) {
                b |= 0x80;  // set MSB for all but the last
            }
            result.add("0x" + Long.toHexString(b & 0xFF));
        }

        return result;
    }

    List<String> decode(List<Long> bytes) {
        List<String> result = new ArrayList<>();
        long current = 0;
        boolean expectingMore = false;

        for (long b : bytes) {
            if (b > 0xFF || b < 0) {
                throw new IllegalArgumentException("Byte out of range"); // optional sanity check
            }

            current = (current << 7) | (b & 0x7F);
            expectingMore = (b & 0x80) != 0;

            if (!expectingMore) {
                result.add(String.format("0x%x", current));
                current = 0;
            }
        }

        if (expectingMore) {
            throw new IllegalArgumentException("Invalid variable-length quantity encoding");
        }

        return result;
    }
}
