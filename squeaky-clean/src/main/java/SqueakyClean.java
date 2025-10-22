import java.util.Arrays;
import java.util.stream.Collectors;

class SqueakyClean {
    static String clean(String identifier) {
        String result = identifier.replaceAll(" ","_");
        result = result.replaceAll(" ","_");
        if (result.contains("-")){
            String firstLetter = result.substring(0, 1);
            String[] resultArr= result.splitWithDelimiters("-",0);
            result = Arrays.stream(resultArr).map(CustomStringUtils::capitalize).collect(Collectors.joining());
            result= firstLetter + result.substring(1);
        }
        result = result.replaceAll("4","a");
        result = result.replaceAll("3","e");
        result = result.replaceAll("0","o");
        result = result.replaceAll("1","l");
        result = result.replaceAll("7","t");
        result = result.replaceAll("[^a-zA-Z_]","");
        return result;
    }

    static class CustomStringUtils {
        static public String capitalize(String s) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
    }
}
