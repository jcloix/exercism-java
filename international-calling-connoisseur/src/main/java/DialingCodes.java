import java.util.HashMap;
import java.util.Map;

public class DialingCodes {
    Map<Integer,String> codes = new HashMap<>();

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setDialingCode(Integer code, String country) {
        codes.put(code,country);
    }

    public String getCountry(Integer code) {
        return codes.get(code);
    }

    public void addNewDialingCode(Integer code, String country) {
        if(codes.containsKey(code)) return;
        if(codes.containsValue(country)) return;
        setDialingCode(code, country);
    }

    public Integer findDialingCode(String country) {
        return codes.entrySet().stream()
                .filter(q->q.getValue().equals(country))
                .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public void updateCountryDialingCode(Integer code, String country) {
        Integer currCode = findDialingCode(country);
        if(currCode == null) return;
        setDialingCode(code, country);
        codes.remove(currCode);
    }
}
