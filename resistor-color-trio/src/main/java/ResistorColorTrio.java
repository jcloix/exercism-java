import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

class ResistorColorTrio {
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

    String label(String[] colors) {
        StringBuilder sb = new StringBuilder();
        sb.append(COLOR_MAP.get(colors[0]));
        sb.append(COLOR_MAP.get(colors[1]));
        double base = Double.parseDouble(sb.toString());
        base*=Math.pow(10,COLOR_MAP.get(colors[2]));
        int i=0;
        while (base > 1000) {
            base/=1000;
            i++;
        }
        DecimalFormat df = new DecimalFormat("0.##"); // No trailing .0

        return df.format(base) + " " + getUnit(i)+ "ohms";
    }

    public String getUnit(Integer i) {
        return switch (i) {
            case 1 -> "kilo";
            case 2 -> "mega";
            case 3 -> "giga";
            default -> "";
        };
    }
}
