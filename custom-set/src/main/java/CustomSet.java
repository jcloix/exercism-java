import java.util.*;
import java.util.stream.Stream;

class CustomSet<T> {
    private final Map<Integer, List<T>> hashMap;

    CustomSet() {
        this.hashMap = new HashMap<>();
    }

    CustomSet(Collection<T> data) {
        this();
        for (T e : data) add(e);
    }

    boolean isEmpty() {
        return hashMap.isEmpty();
    }

    boolean contains(T element) {
        int hashcode = element.hashCode();
        if (!hashMap.containsKey(hashcode)) return false;
        List<T> elements = hashMap.get(hashcode);
        for (T e : elements) {
            if (e.equals(element)) return true;
        }
        return false;
    }

    boolean add(T element) {
        List<T> elements = hashMap.computeIfAbsent(element.hashCode(), ArrayList::new);
        for (T e : elements) {
            if (e.equals(element)) return false;
        }
        elements.add(element);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomSet<?> other)) return false;

        // Compare elements ignoring type parameter
        List<?> otherElements = other.getAll();
        List<T> thisElements = this.getAll();

        return thisElements.size() == otherElements.size() &&
                thisElements.containsAll(otherElements);
    }

    @Override
    public int hashCode() {
        // hash should not depend on bucket layout, just elements
        return Objects.hash(new HashSet<>(getAll()));
    }

    boolean isDisjoint(CustomSet<T> other) {
        return this.getAll().stream().noneMatch(other::contains);
    }

    CustomSet<T> getIntersection(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>();
        this.getAll().stream()
                .filter(other::contains)
                .forEach(result::add);
        return result;
    }

    CustomSet<T> getUnion(CustomSet<T> other) {
        CustomSet<T> union = new CustomSet<>();
        Stream.concat(this.hashMap.values().stream(),
                        other.hashMap.values().stream())
                .flatMap(List::stream)
                .forEach(union::add);
        return union;
    }

    CustomSet<T> getDifference(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>();
        this.getAll().stream()
                .filter(e -> !other.contains(e))
                .forEach(result::add);
        return result;
    }

    boolean isSubset(CustomSet<T> other) {
        return other.getAll().stream().allMatch(this::contains);
    }

    private List<T> getAll() {
        return hashMap.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }
}
