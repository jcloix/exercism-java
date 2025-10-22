import java.util.regex.Pattern;

class Markdown {

    private final String UL = "ul";
    private final String LI = "li";
    private final String H = "h";
    private final String P = "p";
    private static final Pattern BOLD_PATTERN = Pattern.compile("__(.+?)__");
    private static final Pattern ITALIC_PATTERN = Pattern.compile("_(.+?)_");

    String parse(String markdown) {
        final State state = new State();
        StringBuilder sb = new StringBuilder();
        markdown.lines().forEach(line-> {
            sb.append(wrapLine(line,state));
        });
        sb.append(closeListIfNeeded(null,state));
        return sb.toString();
    }

    String parseInline(String line) {
        String result = BOLD_PATTERN.matcher(line).replaceAll("<strong>$1</strong>");
        result = ITALIC_PATTERN.matcher(result).replaceAll("<em>$1</em>");
        return result;
    }

    String closeListIfNeeded(String line,State state) {
        // Close <ul> if we are inside a list and the current line is not a list item
        if(state.isActiveList() && (line == null || !line.startsWith("* "))) {
            state.setActiveList(false);
            return closeTag(UL);
        }
        return "";
    }

    String wrapLine (String line, State state) {
        // Check if we need to close list.
        String closeTag = closeListIfNeeded(line,state);
        // Handle headers and lists
        String formattedLine = handleWrapStart(line,state);
        // Handle tags inside the line
        formattedLine = parseInline(formattedLine);
        return closeTag + formattedLine;
    }

    String handleWrapStart(String line, State state) {
        // Handle Headers h1->h6 - skip if attempt of h7
        if(line.matches("#{1,6} .*")) {
            int headSize = countLeadingChars(line,'#');
            return wrap(line.substring(headSize+1),H+headSize);
        }
        // Handle list (can be start of list or ongoing list)
        if (line.startsWith("* ")) {
            String result = state.isActiveList() ? wrap(line.substring(2),LI): openTag(UL) + wrap(line.substring(2),LI);
            state.setActiveList(true);
            return result;
        }
        // Otherwise wrap in <p>
        return wrap(line,P);
    }

    private int countLeadingChars(String line, char c) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == c) count++;
        return count;
    }

    String wrap(String part,String tag) {
        return openTag(tag) + part+ closeTag(tag);
    }

    String openTag(String tag) {
        return "<"+tag+">";
    }

    String closeTag(String tag) {
        return "</"+tag+">";
    }

    private static class State {
        private boolean isActiveList = false;

        public State() {
        }

        public boolean isActiveList() {
            return isActiveList;
        }

        public void setActiveList(boolean activeList) {
            isActiveList = activeList;
        }
    }
}
