import java.util.Map;
import java.util.function.BiFunction;

class WordProblemSolver {
    private final static String START_QUESTION="What is ";
    private final static Map<String, BiFunction<Integer,Integer,Integer>> OPERATION_MAP =
            Map.ofEntries(
                    Map.entry("plus", (a,b) -> a+b),
                    Map.entry("minus",(a,b) -> a-b),
                    Map.entry("multiplied by",(a,b) -> a*b),
                    Map.entry("divided by",(a,b) -> a/b)
            );
    int solve(final String wordProblem) {
        if(!wordProblem.startsWith(START_QUESTION)) {
            throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
        }
        String clean = wordProblem.substring(START_QUESTION.length());
        Integer base = getNextInt(clean);
        if(base==null) {
            throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
        }
        clean = removeNumber(clean,base);
        if("?".equals(clean)) {
            return base;
        }
        while(true) {
            String operation = getOperation(clean.substring(1));
            if(operation==null) {
                throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
            }
            clean = clean.substring(operation.length()+2);
            Integer secondNumber = getNextInt(clean);
            if(secondNumber==null) {
                throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
            }
            clean = removeNumber(clean,secondNumber);
            base = OPERATION_MAP.get(operation).apply(base,secondNumber);
            if("?".equals(clean)) {
                return base;
            }
        }
    }

    Integer getNextInt(final String wordProblem) {
        StringBuilder sb = new StringBuilder();
        String clean = wordProblem.startsWith("-") ? wordProblem.substring(1):wordProblem;
        int multiplier = wordProblem.startsWith("-") ? -1:1;
        for(Character c : clean.toCharArray()) {
            if(!Character.isDigit(c)) {
                break;
            }
            sb.append(c);
        }
        return sb.isEmpty() ? null:Integer.parseInt(sb.toString())*multiplier;
    }

    String removeNumber(final String wordProblem, Integer value) {
        if(value== null) return wordProblem;
        return wordProblem.substring(value.toString().length());
    }

    String getOperation(final String wordProblem) {
        return OPERATION_MAP.keySet().stream().filter(wordProblem::startsWith).findFirst().orElse(null);
    }
}
