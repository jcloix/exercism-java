import java.util.*;
import java.util.function.Consumer;

class ForthEvaluator {

    private static final Map<String, Consumer<Deque<Integer>>> OPERATIONS_MAP = Map.of(
            "+", stack -> {
                validateRequireNumber(stack, 2, "Addition");
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b + a);
            },
            "-", stack -> {
                validateRequireNumber(stack, 2, "Subtraction");
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b - a);
            },
            "*", stack -> {
                validateRequireNumber(stack, 2, "Multiplication");
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b * a);
            },
            "/", stack -> {
                validateRequireNumber(stack, 2, "Division");
                int a = stack.pop();
                int b = stack.pop();
                if (a == 0) {
                    throw new IllegalArgumentException("Division by 0 is not allowed");
                }
                stack.push(b / a);
            },
            "dup", stack -> {
                validateRequireNumber(stack, 1, "Duplicating");
                stack.push(stack.peek());
            },
            "drop", stack -> {
                validateRequireNumber(stack, 1, "Dropping");
                stack.pop();
            },
            "swap", stack -> {
                validateRequireNumber(stack, 2, "Swapping");
                int a = stack.pop();
                int b = stack.pop();
                stack.push(a);
                stack.push(b);
            },
            "over", stack -> {
                validateRequireNumber(stack, 2, "Overing");
                int a = stack.pop();
                int b = stack.peek();
                stack.push(a);
                stack.push(b);
            }
    );

    private final Map<String, List<String>> DEFINED_COMMAND_MAP = new HashMap<>();

    private static void validateRequireNumber(Deque<Integer> stack, int nbRequired, String operation) {
        if (stack.size() < nbRequired) {
            throw new IllegalArgumentException(operation + " requires that the stack contain at least " + nbRequired + " value" +
                    (nbRequired > 1 ? "s" : ""));
        }
    }

    List<Integer> evaluateProgram(List<String> input) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String command : input) {
            handleCommand(stack, command);
        }
        List<Integer> result = new ArrayList<>(stack);
        Collections.reverse(result); // preserve left-to-right order
        return result;
    }

    private void handleCommand(Deque<Integer> stack, String command) {
        String[] tokens = command.toLowerCase().split("\\s+"); // case-insensitive
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (":".equals(token)) {
                i = defineCustomCommand(tokens, i);
            } else {
                executeToken(stack, token);
            }
        }
    }

    private int defineCustomCommand(String[] tokens, int index) {
        if (index + 1 >= tokens.length) {
            throw new IllegalArgumentException("Invalid definition: missing command name");
        }

        String name = tokens[++index];
        if (name.matches("-?\\d+")) {
            throw new IllegalArgumentException("Cannot redefine numbers");
        }

        List<String> body = new ArrayList<>();
        while (++index < tokens.length && !";".equals(tokens[index])) {
            if(DEFINED_COMMAND_MAP.containsKey(tokens[index])) {
                body.addAll(DEFINED_COMMAND_MAP.get(tokens[index]));
            } else {
                body.add(tokens[index]);
            }
        }

        if (index == tokens.length) {
            throw new IllegalArgumentException("Missing ';' to end definition of " + name);
        }

        DEFINED_COMMAND_MAP.put(name.toLowerCase(), body);
        return index; // skip until after ";"
    }

    private void executeToken(Deque<Integer> stack, String token) {
        if (token.matches("-?\\d+")) { // number
            stack.push(Integer.parseInt(token));
        } else if (DEFINED_COMMAND_MAP.containsKey(token.toLowerCase())) { // user-defined
            for (String inner : DEFINED_COMMAND_MAP.get(token.toLowerCase())) {
                executeToken(stack, inner);
            }
        } else if (OPERATIONS_MAP.containsKey(token)) { // built-in
            OPERATIONS_MAP.get(token).accept(stack);
        }  else {
            throw new IllegalArgumentException("No definition available for operator \"" + token + "\"");
        }
    }
}
