import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class PythagoreanTriplet {
    private final int a;
    private final int b;
    private final int c;

    PythagoreanTriplet(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    static TripletListBuilder makeTripletsList() {
        return new TripletListBuilder();
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PythagoreanTriplet)) return false;
        PythagoreanTriplet that = (PythagoreanTriplet) o;
        return a == that.a && b == that.b && c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    @Override
    public String toString() {
        return String.format("{%d, %d, %d}", a, b, c);
    }

    static class TripletListBuilder {
        private Integer sum = null;
        private Integer maxFactor = null;

        TripletListBuilder thatSumTo(int sum) {
            this.sum = sum;
            return this;
        }

        TripletListBuilder withFactorsLessThanOrEqualTo(int maxFactor) {
            this.maxFactor = maxFactor;
            return this;
        }

        List<PythagoreanTriplet> build() {
            int upperBound = Math.min(sum/2,maxFactor != null ? maxFactor : sum);

            if (upperBound == 0) {
                return Collections.emptyList();
            }

            List<PythagoreanTriplet> result = new ArrayList<>();

            for (int a = 1; a < upperBound; a++) {
                for (int b = a + 1; b < upperBound; b++) {
                    int cSquared = a * a + b * b;
                    int c = (int) Math.sqrt(cSquared);
                    if (c > upperBound || c * c != cSquared) continue; // not a perfect square

                    if (a + b + c == sum) {
                        result.add(new PythagoreanTriplet(a, b, c));
                    }
                }
            }
            return result;
        }
    }
}
