import java.util.List;
import java.util.stream.IntStream;

class LuhnValidator {

    boolean isValid(String candidate) {
        String clean = candidate.replaceAll(" ","");
        if(!validate(clean)) {
            return false;
        }
        int originalCheckDigit = Integer.valueOf(clean.substring(clean.length()-1));
        String reverse = new StringBuilder(clean.substring(0,clean.length()-1)).reverse().toString();
        int sum = IntStream.range(0, reverse.length()).map(q-> {
            int digit = reverse.charAt(q) - '0';
            if(q%2==0) {
                digit*=2;
                digit=digit>9?digit-9:digit;
            }
            return digit;
        }).sum();
        return originalCheckDigit == ((10 - (sum%10))%10);

    }

    boolean validate(String s) {
        return s.matches("[0-9]+") && !s.equals("0");
    }

}
