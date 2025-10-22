import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BaseConverter {
    double value;
    BaseConverter(int originalBase, int[] originalDigits) {
        if(originalBase < 2) {
            throw new IllegalArgumentException("Bases must be at least 2.");
        }
        this.value = getBaseValue(originalBase,originalDigits);
    }

    int[] convertToBase(int newBase) {
        if(newBase < 2) {
            throw new IllegalArgumentException("Bases must be at least 2.");
        }
        if(value==0) {
            return new int[]{0};
        }
        List<Integer> result = new ArrayList<>();
        double remaining = value;
        while(remaining > 0) {
            int tmp = (int)remaining % newBase;
            result.add(tmp);
            remaining=(remaining-tmp)/newBase;
        }
        Collections.reverse(result);
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    double getBaseValue(int base, int[] digits) {
        double result=0;
        for(int i=0;i<digits.length;i++) {
            if(digits[i] >= base) {
                throw new IllegalArgumentException("All digits must be strictly less than the base.");
            }
            if(digits[i] < 0) {
                throw new IllegalArgumentException("Digits may not be negative.");
            }
            result += digits[i] * Math.pow(base,digits.length-i-1);
        }
        return result;
    }

}