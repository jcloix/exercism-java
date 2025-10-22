import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

class ResistorColor {
    private static final Map<String, Integer> COLOR_MAP;
    static {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("black", 0);
        temp.put("brown", 1);
        temp.put("red", 2);
        temp.put("orange", 3);
        temp.put("yellow", 4);
        temp.put("green", 5);
        temp.put("blue", 6);
        temp.put("violet", 7);
        temp.put("grey", 8);
        temp.put("white", 9);
        COLOR_MAP = Collections.unmodifiableMap(temp);
    }
    int colorCode(String color) {
        return COLOR_MAP.get(color.toLowerCase());
    }

    String[] colors() {
        return COLOR_MAP.keySet().toArray(new String[0]);
    }
}
