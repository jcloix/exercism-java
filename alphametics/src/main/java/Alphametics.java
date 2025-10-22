import java.util.*;

class Alphametics {
    private final String puzzle;
    private Set<Character> uniqueLetters;
    private List<String> leftWords;
    private String rightWord;
    private Set<Character> leadingLetters;

    Alphametics(String userInput) {
        this.puzzle = userInput.replaceAll("\s+", "");
        parsePuzzle();

    }

    Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        List<Character> letters = new ArrayList<>(uniqueLetters);
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) digits.add(i);

        Map<Character,Integer> res = permuteAndSolve(letters, digits, new HashMap<>(), 0);
        if(res == null) {
            throw new UnsolvablePuzzleException();
        }
        return res;
    }

    private void parsePuzzle() {
        String[] sides = puzzle.split("==");
        leftWords = Arrays.asList(sides[0].split("\\+"));
        rightWord = sides[1];

        uniqueLetters = new HashSet<>();
        leadingLetters = new HashSet<>();
        for (String word : leftWords) {
            for (char c : word.toCharArray()) uniqueLetters.add(c);
            leadingLetters.add(word.charAt(0));
        }
        for (char c : rightWord.toCharArray()) uniqueLetters.add(c);
        leadingLetters.add(rightWord.charAt(0));

        if (uniqueLetters.size() > 10)
            throw new IllegalArgumentException("Too many unique letters (max 10). Found: " + uniqueLetters.size());
    }

    private Map<Character, Integer> permuteAndSolve(List<Character> letters, List<Integer> digits,
                                                    Map<Character, Integer> mapping, int index) throws UnsolvablePuzzleException{
        if (index == letters.size()) {
            if (isValid(mapping)) return new HashMap<>(mapping);
            return null;
        }

        Character current = letters.get(index);
        for (Integer digit : digits) {
            if (mapping.containsValue(digit)) continue;
            if (digit == 0 && leadingLetters.contains(current)) continue;

            mapping.put(current, digit);
            Map<Character, Integer> result = permuteAndSolve(letters, digits, mapping, index + 1);
            if (result != null) return result;
            mapping.remove(current);
        }
        return null;
    }

    private boolean isValid(Map<Character, Integer> mapping) throws UnsolvablePuzzleException{
        try {
            long sum = 0;
            for (String word : leftWords) sum += wordToNumber(word, mapping);
            long target = wordToNumber(rightWord, mapping);
            return sum == target;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private long wordToNumber(String word, Map<Character, Integer> mapping) throws UnsolvablePuzzleException{
        if (mapping.get(word.charAt(0)) == 0) throw new UnsolvablePuzzleException();
        long num = 0;
        for (char c : word.toCharArray()) {
            num = num * 10 + mapping.get(c);
        }
        return num;
    }

}