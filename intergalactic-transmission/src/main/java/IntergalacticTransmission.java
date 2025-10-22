import java.util.ArrayList;
import java.util.List;

public class IntergalacticTransmission {

    public static List<Integer> getTransmitSequence(List<Integer> data) {
        List<Integer> bits = new ArrayList<>();

        // Flatten bytes to bit list
        for (int value : data) {
            for (int i = 7; i >= 0; i--) {
                bits.add((value >> i) & 1);
            }
        }

        List<Integer> transmissions = new ArrayList<>();

        for (int i = 0; i < bits.size(); i += 7) {
            int byteWithParity = 0;
            int oneCount = 0;

            // Build 7 bits of data
            for (int j = 0; j < 7; j++) {
                int bit = (i + j < bits.size()) ? bits.get(i + j) : 0;
                if (bit == 1) oneCount++;
                byteWithParity = (byteWithParity << 1) | bit;
            }

            // Add parity bit (to LSB)
            int parity = (oneCount % 2 == 0) ? 0 : 1;
            byteWithParity = (byteWithParity << 1) | parity;

            transmissions.add(byteWithParity);
        }

        return transmissions;
    }

    public static List<Integer> decodeSequence(List<Integer> transmission) {
        List<Integer> bits = new ArrayList<>();

        for (int value : transmission) {
            int oneCount = Integer.bitCount(value);
            if (oneCount % 2 != 0) {
                throw new IllegalArgumentException("Corrupted transmission: odd number of 1s");
            }

            // Strip parity (rightmost bit)
            int dataBits = value >> 1;
            for (int i = 6; i >= 0; i--) {
                bits.add((dataBits >> i) & 1);
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i + 7 < bits.size(); i += 8) {
            int byteVal = 0;
            for (int j = 0; j < 8; j++) {
                byteVal = (byteVal << 1) | bits.get(i + j);
            }
            result.add(byteVal);
        }

        return result;
    }

}
