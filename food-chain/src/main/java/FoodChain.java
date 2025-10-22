import java.util.List;
import java.util.Map;

class FoodChain {
    private final static List<String> ANIMALS = List.of("fly","spider","bird","cat","dog","goat","cow","horse");
    private final static Map<String,String> COMMENT_MAP = Map.ofEntries(
            Map.entry("spider","It wriggled and jiggled and tickled inside her.\n"),
            Map.entry("bird","How absurd to swallow a bird!\n"),
            Map.entry("cat","Imagine that, to swallow a cat!\n"),
            Map.entry("dog","What a hog, to swallow a dog!\n"),
            Map.entry("goat","Just opened her throat and swallowed a goat!\n"),
            Map.entry("cow","I don't know how she swallowed a cow!\n"),
            Map.entry("horse","She's dead, of course!\n")
            );
    private final static Map<String,String> REASON_MAP = Map.ofEntries(
            Map.entry("fly","I don't know why she swallowed the fly. Perhaps she'll die.\n"),
            Map.entry("bird","She swallowed the bird to catch the spider that wriggled and jiggled and tickled inside her.\n")
            );
    private final static String DEFAULT_REASON = "She swallowed the %s to catch the %s.\n";
    private final static String DEFAULT_INFO = "I know an old lady who swallowed a %s.\n";
    String verse(int verse) {
        return verses(verse,verse);
    }

    String verses(int startVerse, int endVerse) {
        if(startVerse < 1 || startVerse > 8 || endVerse < 1 || endVerse > 8) {
           throw new IllegalArgumentException("Verse either too big or too small");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=startVerse-1; i < endVerse;i++) {
            String animal = ANIMALS.get(i);
            sb.append(String.format(DEFAULT_INFO,animal));
            sb.append(String.format(COMMENT_MAP.getOrDefault(animal,"")));
            if(i== ANIMALS.size()-1) {
                sb.append("\n");
                break;
            }
            for(int j = i; j >=0 ;j--) {
                String otherAnimal =ANIMALS.get(j);
                if(REASON_MAP.containsKey(otherAnimal)) {
                    sb.append(REASON_MAP.get(otherAnimal));
                } else {
                    sb.append(String.format(DEFAULT_REASON,ANIMALS.get(j),ANIMALS.get(j-1)));
                }

            }
            sb.append("\n");
        }
        sb.setLength(sb.length()-2);
        return sb.toString();
    }

}