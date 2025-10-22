import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

class ResistorColorDuo {
    private static final Map<String, String> COLOR_MAP;
    static {
        Map<String, String> temp = new LinkedHashMap<>();
        temp.put("black", "0");
        temp.put("brown", "1");
        temp.put("red", "2");
        temp.put("orange", "3");
        temp.put("yellow", "4");
        temp.put("green", "5");
        temp.put("blue", "6");
        temp.put("violet", "7");
        temp.put("grey", "8");
        temp.put("white", "9");
        COLOR_MAP = Collections.unmodifiableMap(temp);
    }
    int value(String[] colors) {
        StringBuilder sb = new StringBuilder();
        sb.append(COLOR_MAP.get(colors[0]));
        sb.append(COLOR_MAP.get(colors[1]));
        return Integer.valueOf(sb.toString());
    }
}
