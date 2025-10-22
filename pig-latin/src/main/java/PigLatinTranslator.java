import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PigLatinTranslator {
    private final static Set<Character> VOWELS = Set.of('a','e','i','o','u');
    private final static Set<Character> CONSONANTS = IntStream.rangeClosed('a','z')
            .mapToObj(c->(char)c)
            .filter(c-> !VOWELS.contains(c))
            .collect(Collectors.toSet());

    public String translate(String word) {
        if(word.contains(" ")) {
            String[] words = word.split(" ");
            List<String> result = new ArrayList<>();
            for(String w : words) {
                result.add(translateWord(w));
            }
            return String.join(" ", result);
        }
        return translateWord(word);
    }

    public String translateWord(String word) {
        if(rule1(word)) {
            return word+"ay";
        } else if (rule3(word)) {
            int index = word.indexOf("qu");
            return word.substring(index+2) + word.substring(0,index+2) + "ay";
        } else if (rule4(word)) {
            int index = word.indexOf("y");
            return word.substring(index) + word.substring(0,index) + "ay";
        } else if (rule2(word)) {
            int index = IntStream.range(0,word.length()).filter(i->VOWELS.contains(word.charAt(i))).findFirst().orElse(-1);
            if(index==-1) {
                return word;
            }
            return word.substring(index) + word.substring(0,index)+"ay";
        }
        return word;
    }

    public boolean rule1(String word) {
        return VOWELS.contains(word.charAt(0))  || word.startsWith("xr") || word.startsWith("yt");
    }

    public boolean rule2(String word) {
        return CONSONANTS.contains(word.charAt(0));
    }

    public boolean rule3(String word) {
        int index = IntStream.range(0,word.length()).filter(i->VOWELS.contains(word.charAt(i))).findFirst().orElse(-1);
        return index != -1 && "qu".equals(word.substring(index-1,index+1));
    }

    public boolean rule4(String word) {
        int index = IntStream.range(0,word.length()).filter(i->VOWELS.contains(word.charAt(i)) || word.charAt(i) == 'y').findFirst().orElse(-1);
        return index >0 && 'y' == word.charAt(index);
    }




}