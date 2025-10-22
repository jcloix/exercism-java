import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

class Allergies {
    Set<Allergen> allergens = new LinkedHashSet<>();
    Allergies(int score) {
        String binary = new StringBuilder(Integer.toBinaryString(score)).reverse().toString();
        IntStream.range(0,Math.min(binary.length(),8)).forEach(i->{
            if(binary.charAt(i) == '1') {
                allergens.add(Allergen.values()[i]);
            }
        });
    }

    boolean isAllergicTo(Allergen allergen) {
        return allergens.contains(allergen);
    }

    List<Allergen> getList() {
        return allergens.stream().toList();
    }
}
