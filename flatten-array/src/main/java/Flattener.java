import java.util.ArrayList;
import java.util.List;

class Flattener {

    List<Object> flatten(List<?> list) {
        List<Object> result = new ArrayList<>();
        for(Object l : list) {
            if(l == null) continue;
            if(l instanceof List<?>) {
                result.addAll(flatten((List<?>)l));
            } else {
                result.add(l);
            }
        }
        return result;
    }


}