import java.util.*;

public class SgfParsing {
    private int index;
    private String input;
    public SgfNode parse(String input) throws SgfParsingException {
        validate(input);
        this.input = input;
        this.index = 0;

        SgfNode root = parseGameTree();
        return root;
    }

    public void validate(String input) throws SgfParsingException {
        if(input.isBlank() || ";".equals(input)) {
            throw new SgfParsingException("tree missing");
        }
        if("()".equals(input)) {
            throw new SgfParsingException("tree with no nodes");
        }
    }

    private SgfNode parseGameTree() throws SgfParsingException {
        consume('(');
        SgfNode sequenceRoot = parseSequence();
        consume(')');
        return sequenceRoot;
    }

    private SgfNode parseSequence() throws SgfParsingException {
        if (peek() != ';') {
            throw new SgfParsingException("Expected ';' at sequence start");
        }

        SgfNode first = null;
        SgfNode current = null;

        while (peek() == ';') {
            SgfNode node = parseNode();
            if (first == null) {
                first = node;
            }
            if (current != null) {
                current.appendChild(node);
            }
            current = node;

            // Handle variations
            while (peek() == '(') {
                current.appendChild(parseGameTree());
            }
        }

        return first;
    }

    private SgfNode parseNode() throws SgfParsingException {
        consume(';');
        Map<String, List<String>> props = new HashMap<>();
        SgfNode node = new SgfNode();
        node.setProperties(props);

        while (Character.isUpperCase(peek())) {
            String key = parseKey();
            if (props.containsKey(key)) {
                throw new SgfParsingException("Duplicate property: " + key);
            }
            List<String> values = new ArrayList<>();
            do {
                values.add(parseValue());
            } while (peek() == '[');
            props.put(key, values);
        }

        return node;
    }

    private String parseKey() throws SgfParsingException {
        StringBuilder sb = new StringBuilder();
        while (Character.isUpperCase(peek())) {
            sb.append(consume());
        }
        return sb.toString();
    }

    private String parseValue() throws SgfParsingException {
        consume('[');
        StringBuilder sb = new StringBuilder();

        while (peek() != ']') {
            char c = consume();
            if (c == '\\') {
                char next = consume();
                if (next == '\n') {
                    // skip newline
                } else if (Character.isWhitespace(next) && next != '\n') {
                    sb.append(' ');
                } else {
                    sb.append(next);
                }
            } else if (Character.isWhitespace(c) && c != '\n') {
                sb.append(' ');
            } else {
                sb.append(c);
            }
        }

        consume(']');
        return sb.toString();
    }

    // --- Helpers ---
    private char peek() throws SgfParsingException {
        if (index >= input.length()) {
            throw new SgfParsingException("Unexpected end of input");
        }
        return input.charAt(index);
    }

    private char consume() throws SgfParsingException {
        if (index >= input.length()) {
            throw new SgfParsingException("Unexpected end of input");
        }
        return input.charAt(index++);
    }

    private void consume(char expected) throws SgfParsingException {
        char c = consume();
        if (c != expected) {
            throw new SgfParsingException("Expected '" + expected + "' but found '" + c + "'");
        }
    }
}
