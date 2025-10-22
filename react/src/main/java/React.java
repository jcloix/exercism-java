import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class React {

    public static abstract class Cell<T> {
        protected T value;
        protected final List<ComputeCell<T>> dependents = new ArrayList<>();

        public T getValue() {
            return value;
        }
    }

    public static class InputCell<T> extends Cell<T> {
        public InputCell(T initialValue) {
            this.value = initialValue;
        }

        public void setValue(T newValue) {
            if (!Objects.equals(this.value, newValue)) {
                this.value = newValue;
                propagate();
            }
        }

        private void propagate() {
            Deque<ComputeCell<T>> queue = new ArrayDeque<>(dependents);
            // Cells that actually changed during this update (for callbacks after stability)
            Set<ComputeCell<T>> changed = new LinkedHashSet<>();

            while (!queue.isEmpty()) {
                ComputeCell<T> cell = queue.removeFirst();

                T oldValue = cell.value;
                T newValue = cell.compute();

                if (!Objects.equals(oldValue, newValue)) {
                    cell.value = newValue;
                    changed.add(cell);
                    // Only enqueue further if this cell actually changed
                    for (ComputeCell<T> dep : cell.dependents) {
                        queue.addLast(dep);
                    }
                }
                // IMPORTANT: no "visited" set. We allow multiple passes until stability.
            }

            // Stable state reached — fire callbacks exactly once per changed cell
            for (ComputeCell<T> cell : changed) {
                cell.fireCallbacks();
            }
        }
    }

    public static class ComputeCell<T> extends Cell<T> {
        private final Function<List<T>, T> function;
        private final List<Cell<T>> dependencies;
        private final List<Consumer<T>> callbacks = new ArrayList<>();

        public ComputeCell(Function<List<T>, T> function, List<Cell<T>> dependencies) {
            this.function = function;
            this.dependencies = List.copyOf(dependencies);
            this.value = compute();
            for (Cell<T> dep : this.dependencies) {
                dep.dependents.add(this);
            }
        }

        private T compute() {
            List<T> args = new ArrayList<>(dependencies.size());
            for (Cell<T> dep : dependencies) {
                args.add(dep.getValue());
            }
            return function.apply(args);
        }

        public void addCallback(Consumer<T> callback) {
            callbacks.add(callback);
        }

        public void removeCallback(Consumer<T> callback) {
            callbacks.remove(callback);
        }

        private void fireCallbacks() {
            // Fire in registration order
            for (Consumer<T> cb : callbacks) {
                cb.accept(value);
            }
        }
    }

    public static <T> InputCell<T> inputCell(T initialValue) {
        return new InputCell<>(initialValue);
    }

    public static <T> ComputeCell<T> computeCell(Function<List<T>, T> function, List<Cell<T>> cells) {
        return new ComputeCell<>(function, cells);
    }
}
