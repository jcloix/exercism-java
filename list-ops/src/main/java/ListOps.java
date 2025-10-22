import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class ListOps {
    static <T> List<T> append(List<T> list1, List<T> list2) {
        List<T> results = new ArrayList<>(list1);
        results.addAll(list2);
        return results;
    }

    static <T> List<T> concat(List<List<T>> listOfLists) {
        List<T> results = new ArrayList<>();
        listOfLists.forEach(results::addAll);
        return results;
    }

    static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T e : list) {
            if(predicate.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    static <T> int size(List<T> list) {
        AtomicInteger i = new AtomicInteger(0);
        list.forEach(q->i.getAndIncrement());
        return i.get();
    }

    static <T, U> List<U> map(List<T> list, Function<T, U> transform) {
        List<U> result = new ArrayList<>();
        for (T e : list) {
            result.add(transform.apply(e));
        }
        return result;
    }

    static <T> List<T> reverse(List<T> list) {
        List<T> result = new ArrayList<>();
        for(int i = size(list); i>0;i--) {
            result.add(list.get(i-1));
        }
        return result;
    }

    static <T, U> U foldLeft(List<T> list, U initial, BiFunction<U, T, U> f) {
        U result = initial;
        for(T e : list) {
            result = f.apply(result,e);
        }
        return result;
    }

    static <T, U> U foldRight(List<T> list, U initial, BiFunction<T, U, U> f) {
        U result = initial;
        for(T e : reverse(list)) {
            result = f.apply(e,result);
        }
        return result;
    }

    private ListOps() {
        // No instances.
    }

}
