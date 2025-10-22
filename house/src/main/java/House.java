import java.util.List;
import java.util.Map;

class House {
    private static final List<String> SUBJECT =
            List.of("Jack","malt","rat","cat","dog","cow",
                    "maiden","man","priest","rooster",
                    "farmer","horse");

    private static final Map<String,String> ACTION_MAP = Map.ofEntries(
            Map.entry("malt","that lay in the house that Jack built."),
            Map.entry("rat","that ate the malt "),
            Map.entry("cat","that killed the rat "),
            Map.entry("dog","that worried the cat "),
            Map.entry("cow","that tossed the dog "),
            Map.entry("maiden","that milked the cow with the crumpled horn "),
            Map.entry("man","that kissed the maiden all forlorn "),
            Map.entry("priest","that married the man all tattered and torn "),
            Map.entry("rooster","that woke the priest all shaven and shorn "),
            Map.entry("farmer","that kept the rooster that crowed in the morn "),
            Map.entry("horse","that belonged to the farmer sowing his corn ")
    );

    private static final String DEFAULT_EXPL = "This is the %s ";
    private static final Map<String,String> SPECIAL_EXPL_MAP = Map.ofEntries(
            Map.entry("Jack","This is the house that Jack built."),
            Map.entry("cow","This is the cow with the crumpled horn "),
            Map.entry("maiden","This is the maiden all forlorn "),
            Map.entry("man","This is the man all tattered and torn "),
            Map.entry("priest","This is the priest all shaven and shorn "),
            Map.entry("rooster","This is the rooster that crowed in the morn "),
            Map.entry("farmer","This is the farmer sowing his corn "),
            Map.entry("horse","This is the horse and the hound and the horn ")
    );

    String verse(int verse) {
        return verses(verse,verse);
    }

    String verses(int startVerse, int endVerse) {
        if(startVerse < 1 || endVerse < 1 || startVerse > SUBJECT.size() || endVerse > SUBJECT.size()) {
            throw new IllegalArgumentException("Invalid verses");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=startVerse-1;i<endVerse;i++) {
            sb.append(getExplanation(SUBJECT.get(i)));
            for(int j=i;j>0;j--) {
                sb.append(getAction(SUBJECT.get(j)));
            }
            sb.append("\n");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }

    String sing() {
        return verses(1,SUBJECT.size());
    }

    String getExplanation(String subject) {
        return SPECIAL_EXPL_MAP.getOrDefault(subject,String.format(DEFAULT_EXPL,subject));
    }

    String getAction(String subject) {
        return ACTION_MAP.get(subject);
    }

}