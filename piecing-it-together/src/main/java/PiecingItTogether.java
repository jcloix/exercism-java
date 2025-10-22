import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class PiecingItTogether {
    Map<String, List<Consumer<JigsawInfo>>> subscribeMap = Map.ofEntries(
            Map.entry("pieces",List.of(
                    l->{
                        if(l.getRows().isPresent() && l.getColumns().isPresent()) {

                        }
                    },
                    l->{}))
    );

    public static JigsawInfo getCompleteInformation2(JigsawInfo input) {
        // pieces = rows + square
        // pieces = rows + columns
        // pieces = inside + border

        // format = ratio
        // ratio = format

        // border = ratio + inside
        // inside = ratio + border
        // border = ratio + pieces
        // inside = border + pieces
        // inside = border + pieces

        // rows = pieces + ratio
        // columns
        throw new UnsupportedOperationException("Delete this statement and write your own implementation.");
    }

    public static JigsawInfo getCompleteInformation(JigsawInfo input) {
        PuzzleSolverReactive solver = new PuzzleSolverReactive(input);
        return solver.build();
    }


}

class PuzzleSolverReactive {
    ReactiveField<Integer> rows = new ReactiveField<>();
    ReactiveField<Integer> columns = new ReactiveField<>();
    ReactiveField<Integer> pieces = new ReactiveField<>();
    ReactiveField<Integer> border = new ReactiveField<>();
    ReactiveField<Integer> inside = new ReactiveField<>();
    ReactiveField<Double> aspectRatio = new ReactiveField<>();
    ReactiveField<String> format = new ReactiveField<>();

    public PuzzleSolverReactive(JigsawInfo input) {

        // Wire inference rules (order-independent)
        rows.subscribe(this::inferPiecesFromRowsColumns);
        columns.subscribe(this::inferPiecesFromRowsColumns);
        rows.subscribe(this::inferAspectRatio);
        columns.subscribe(this::inferAspectRatio);
        aspectRatio.subscribe(this::inferColumnsFromAspectAndRows);
        rows.subscribe(this::inferColumnsFromAspectAndRows);

        rows.subscribe(this::inferBorder);
        columns.subscribe(this::inferBorder);

        pieces.subscribe(this::inferInside);
        border.subscribe(this::inferInside);

        pieces.subscribe(this::inferRowsAndColumnsFromPiecesAndAspectRatio);
        aspectRatio.subscribe(this::inferRowsAndColumnsFromPiecesAndAspectRatio);

        inside.subscribe(this::inferRowsColumnsFromInsideAndAspectRatio);
        aspectRatio.subscribe(this::inferRowsColumnsFromInsideAndAspectRatio);

        aspectRatio.subscribe(this::inferFormat);
        format.subscribe(this::inferAspectRatioFromFormat);

        inside.subscribe(this::inferRowsColumnsFromInsideAndPortraitFormat);
        format.subscribe(this::inferRowsColumnsFromInsideAndPortraitFormat);


        // Initialize known values
        input.getRows().ifPresent(rows::set);
        input.getColumns().ifPresent(columns::set);
        input.getPieces().ifPresent(pieces::set);
        input.getBorder().ifPresent(border::set);
        input.getInside().ifPresent(inside::set);
        input.getAspectRatio().ifPresent(aspectRatio::set);
        input.getFormat().ifPresent(format::set);
    }

    private void validateCoherence() {
        if (format.isPresent() && aspectRatio.isPresent()) {
            double ar = aspectRatio.get();
            String expectedFormat;
            if (Math.abs(ar - 1.0) < 1e-6) {
                expectedFormat = "square";
            } else if (ar < 1.0) {
                expectedFormat = "portrait";
            } else {
                expectedFormat = "landscape";
            }

            if (!format.get().equals(expectedFormat)) {
                throw new IllegalArgumentException("Contradictory data");
            }
        }
    }


    private void inferRowsColumnsFromInsideAndPortraitFormat() {
        if (inside.isPresent() && format.isPresent()
                && !rows.isPresent() && !columns.isPresent()
                && format.get().equals("portrait")) {

            int targetInside = inside.get();

            // Try plausible row/column combinations where c/r < 1.0
            for (int r = 3; r <= 200; r++) {
                for (int c = 3; c < r; c++) { // c < r ⇒ portrait
                    if ((r - 2) * (c - 2) == targetInside) {
                        rows.set(r);
                        columns.set(c);
                        return;
                    }
                }
            }
        }
    }

    private void inferRowsColumnsFromInsideAndAspectRatio() {
        if (!rows.isPresent() && !columns.isPresent()
                && inside.isPresent() && aspectRatio.isPresent()) {

            double ar = aspectRatio.get();     // aspect ratio = columns / rows
            int insideCount = inside.get();    // inside = (rows - 2) * (columns - 2)

            // We assume a square puzzle with border width of 1,
            // so inside = (rows - 2) * (columns - 2)
            // and columns = ar * rows

            // Try plausible rows
            for (int r = 1; r <= 500; r++) {
                int c = (int) Math.round(r * ar);
                if (Math.abs((double)c / r - ar) > 0.01) continue; // allow rounding error
                int inner = (r - 2) * (c - 2);
                if (inner == insideCount) {
                    rows.set(r);
                    columns.set(c);
                    return;
                }
            }
        }
    }

    private void inferPiecesFromRowsColumns() {
        if (rows.isPresent() && columns.isPresent() && !pieces.isPresent()) {
            pieces.set(rows.get() * columns.get());
        }
    }

    private void inferAspectRatio() {
        if (rows.isPresent() && columns.isPresent() && !aspectRatio.isPresent()) {
            aspectRatio.set((double) columns.get() / rows.get());
        }
    }

    private void inferColumnsFromAspectAndRows() {
        if (aspectRatio.isPresent() && rows.isPresent() && !columns.isPresent()) {
            columns.set((int) Math.round(rows.get() * aspectRatio.get()));
        }
    }

    private void inferBorder() {
        if (rows.isPresent() && columns.isPresent() && !border.isPresent()) {
            border.set(2 * rows.get() + 2 * columns.get() - 4);
        }
    }

    private void inferInside() {
        if (pieces.isPresent() && border.isPresent() && !inside.isPresent()) {
            inside.set(pieces.get() - border.get());
        }
    }

    private void inferRowsAndColumnsFromPiecesAndAspectRatio() {
        if (pieces.isPresent() && aspectRatio.isPresent() && !rows.isPresent() && !columns.isPresent()) {
            int inferredRows = (int) Math.round(Math.sqrt(pieces.get() / aspectRatio.get()));
            int inferredCols = (int) Math.round(pieces.get() / (double) inferredRows);
            rows.set(inferredRows);
            columns.set(inferredCols);
        }
    }

    private void inferFormat() {
        if (aspectRatio.isPresent() && !format.isPresent()) {
            double ar = aspectRatio.get();
            if (Math.abs(ar - 1.0) < 1e-9) {
                format.set("square");
            } else if (ar < 1.0) {
                format.set("portrait");
            } else {
                format.set("landscape");
            }
        }
    }

    private void inferAspectRatioFromFormat() {
        if (format.isPresent() && format.get().equals("square") && !aspectRatio.isPresent()) {
            aspectRatio.set(1.0);
        }
    }

    public JigsawInfo build() {
        if (!rows.isPresent() || !columns.isPresent() || !pieces.isPresent()
                || !border.isPresent() || !inside.isPresent()
                || !aspectRatio.isPresent() || !format.isPresent()) {
            throw new IllegalArgumentException("Insufficient data to solve puzzle");
        }


        validateCoherence();

        return new JigsawInfo.Builder()
                .rows(rows.get())
                .columns(columns.get())
                .pieces(pieces.get())
                .border(border.get())
                .inside(inside.get())
                .aspectRatio(aspectRatio.get())
                .format(format.get())
                .build();
    }
}


class ReactiveField<T> {
    private T value;
    private boolean isSet = false;
    private final List<Runnable> subscribers = new ArrayList<>();

    public void set(T val) {
        if (!isSet || !Objects.equals(val, value)) {
            this.value = val;
            this.isSet = true;
            notifySubscribers();
        }
    }

    public T get() {
        if (!isSet) throw new IllegalStateException("Value not set yet");
        return value;
    }

    public boolean isPresent() {
        return isSet;
    }

    public void subscribe(Runnable r) {
        subscribers.add(r);
    }

    private void notifySubscribers() {
        for (Runnable r : subscribers) {
            r.run();
        }
    }
}

