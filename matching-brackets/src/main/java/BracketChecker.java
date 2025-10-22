import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BracketChecker {

    Set<Character> open = Set.of('{','[','(');
    Set<Character> close = Set.of('}',']',')');
    Set<Character> all = Stream.of(open,close)
            .flatMap(Set::stream).collect(Collectors.toSet());
    Map<Character,Character> pair = Map.of('{','}','[',']','(',')');
    boolean isValid;


    BracketChecker(String expression) {
        Stack<Character> stack = new Stack<>();
        Character issueChar = expression.chars()
                .mapToObj(c->(char)c).filter(c-> {
                    if(!all.contains(c)) return false;
                    Character last = stack.empty() ? null:stack.peek();
                    if(open.contains(c)) {
                        stack.push(c);
                        return false;
                    }
                    if(last != null && pair.get(last) == c) {
                        stack.pop();
                        return false;
                    }
                    return true;
                }).findAny().orElse(null);
        isValid=issueChar == null && stack.empty();
    }

    boolean areBracketsMatchedAndNestedCorrectly() {
        return isValid;
    }


}