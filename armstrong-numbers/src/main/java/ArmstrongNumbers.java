import java.util.stream.IntStream;

class ArmstrongNumbers {

    boolean isArmstrongNumber(int numberToCheck) {
        if(numberToCheck==0) return true;
        String n = Integer.valueOf(numberToCheck).toString();
        return IntStream.range(0,n.length())
                .mapToDouble(q->Math.pow(Character.getNumericValue(n.charAt(q)), n.length())).sum() == numberToCheck;

    }

}
