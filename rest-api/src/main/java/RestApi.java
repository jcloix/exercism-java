import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

class RestApi implements Serializable {
    private static final Map<String, BiFunction<RestApi,JSONObject,String>> GET_MAP = Map.ofEntries(
            Map.entry("/users", (restApi,payload) -> {
                Collection<User> users =
                        payload== null ? restApi.usersMap.values() :
                                payload.getJSONArray("users").toList().stream()
                                        .map(q->restApi.usersMap.get((String)q)).toList();
                return getUsers(users);
            })
    );

    private static final Map<String, BiFunction<RestApi,JSONObject,String>> POST_MAP = Map.ofEntries(
            Map.entry("/add", (restApi,payload) -> {
                String name = payload.getString("user");
                User newUser = User.builder().setName(name).build();
                restApi.usersMap.put(name,newUser);
                return newUser.toJson().toString();
            }),
            Map.entry("/iou", (restApi,payload) -> {
                String lender = payload.getString("lender");
                String borrower = payload.getString("borrower");
                Double amount = payload.getDouble("amount");
                restApi.addDebt(lender,borrower,amount);
                return getUsers(Stream.of(restApi.usersMap.get(lender),restApi.usersMap.get(borrower)).sorted().toList());
            })
    );
    Map<String,User> usersMap = new HashMap<>();
    RestApi(User... users) {
        Arrays.stream(users).forEach(q->usersMap.put(q.name(),q));
    }

    String get(String url) {
        return get(url,null);
    }

    String get(String url, JSONObject payload) {
        return GET_MAP.get(url).apply(this,payload);
    }

    String post(String url, JSONObject payload) {
        return POST_MAP.get(url).apply(this,payload);
    }

    private static String getUsers(Collection<User> users) {
        JSONArray userArray = new JSONArray();
        for (User user : users) {
            userArray.put(user.toJson());
        }
        return new JSONObject().put("users", userArray).toString();
    }

    private void addDebt(String lender, String borrower, Double amount) {
        User lenderUser = usersMap.get(lender);
        User borrowerUser = usersMap.get(borrower);

        // Case 1: Lender is already owed by borrower
        Iou lenderOwedBy = lenderUser.getOwedBy(borrower);
        if (lenderOwedBy != null) {
            lenderUser.changeAmountOwedBy(borrower, lenderOwedBy.amount+amount);
            borrowerUser.changeAmountOwe(lender, borrowerUser.getOwes(lender).amount+amount);
            return;
        }

        // Case 2: Lender owes the borrower (canceling out)
        Iou lenderOwes = lenderUser.getOwes(borrower);
        if (lenderOwes != null) {
            double diff = lenderOwes.amount - amount;
            if (diff > 0) {
                lenderUser.changeAmountOwe(borrower,diff);
                borrowerUser.changeAmountOwedBy(lender,diff);
            } else if (diff < 0) {
                lenderUser.removeOwe(borrower);
                borrowerUser.removeOwedBy(lender);
                lenderUser.addOwedBy(borrower, -diff);
                borrowerUser.addOwe(lender, -diff);
            } else {
                lenderUser.removeOwe(borrower);
                borrowerUser.removeOwedBy(lender);
            }
            return;
        }

        // Case 3: No prior debt — create new IOU
        lenderUser.addOwedBy(borrower, amount);
        borrowerUser.addOwe(lender, amount);
    }

}