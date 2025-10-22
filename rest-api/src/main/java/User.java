import org.json.JSONObject;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** POJO representing a User in the database. */
public class User implements Comparable{
    private final String name;
    private final Map<String, Iou> owes;
    private final Map<String, Iou> owedBy;

    private User(String name, Map<String,Iou> owes, Map<String,Iou> owedBy) {
        this.name = name;
        this.owes = owes;
        this.owedBy = owedBy;
    }

    public String name() {
        return name;
    }

    /** IOUs this user owes to other users. */
    public Map<String,Iou> owes() {
        return unmodifiableMap(owes);
    }

    /** IOUs other users owe to this user. */
    public Map<String,Iou> owedBy() {
        return unmodifiableMap(owedBy);
    }

    public Iou getOwes(String name) {
        return owes.get(name);
    }

    public Iou getOwedBy(String name) {
        return owedBy.get(name);
    }

    public void addOwe(String name, double amount) {
        owes.put(name,new Iou(name, amount));
    }

    public void addOwedBy(String name, double amount) {
        owedBy.put(name,new Iou(name, amount));
    }

    public void removeOwe(String name) {
        owes.remove(name);
    }

    public void removeOwedBy(String name) {
        owedBy.remove(name);
    }

    public void changeAmountOwe(String name, double amount) {
        owes.put(name,new Iou(name, amount));
    }

    public void changeAmountOwedBy(String name, double amount) {
        owedBy.put(name,new Iou(name, amount));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private final Map<String,Iou> owes = new HashMap<>();
        private final Map<String,Iou> owedBy = new HashMap<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder owes(String name, double amount) {
            owes.put(name,new Iou(name, amount));
            return this;
        }

        public Builder owedBy(String name, double amount) {
            owedBy.put(name,new Iou(name, amount));
            return this;
        }

        public User build() {
            return new User(name, owes, owedBy);
        }
    }

    JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                .put("owes", iouListToMap(owes))
                .put("owedBy", iouListToMap(owedBy))
                .put("balance", calculateBalance());
    }

    private static JSONObject iouListToMap(Map<String,Iou> map) {
        JSONObject obj = new JSONObject();
        for (Map.Entry<String,Iou> entry : map.entrySet()) {
            obj.put(entry.getKey(), entry.getValue().amount);
        }
        return obj;
    }

    private double calculateBalance() {
        double owedToMe = owedBy.values().stream().mapToDouble(q->q.amount).sum();
        double iOwe = owes.values().stream().mapToDouble(q->q.amount).sum();
        return Math.round(owedToMe - iOwe); // round to 2 decimals
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((User)o).name);
    }
}
