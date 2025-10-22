import java.util.Map;
import java.util.Objects;

class Rational {
    private final int numerator;
    private final int denominator;

    Rational(int numerator, int denominator) {
        Map.Entry<Integer,Integer> result = reduce(numerator,denominator);
        this.numerator=result.getKey();
        this.denominator=result.getValue();
    }

    int getNumerator() {
        return this.numerator;
    }

    int getDenominator() {
        return this.denominator;
    }

    Rational add(Rational other) {
        // `r₁ + r₂ = a₁/b₁ + a₂/b₂ = (a₁ * b₂ + a₂ * b₁) / (b₁ * b₂)`.
        int numerator = (this.getNumerator() * other.getDenominator()) + (other.getNumerator() * this.getDenominator());
        int den = getDenominator() * other.getDenominator();
        return new Rational(numerator,den);

    }

    Rational subtract(Rational other) {
        // `r₁ - r₂ = a₁/b₁ - a₂/b₂ = (a₁ * b₂ - a₂ * b₁) / (b₁ * b₂)`.
        int numerator = (this.getNumerator() * other.getDenominator()) - (other.getNumerator() * this.getDenominator());
        int den = getDenominator() * other.getDenominator();
        return new Rational(numerator,den);
    }

    Rational multiply(Rational other) {
        // `r₁ * r₂ = (a₁ * a₂) / (b₁ * b₂)`.
        int numerator = this.getNumerator() * other.getNumerator() ;
        int den = getDenominator() * other.getDenominator();
        return new Rational(numerator,den);
    }

    Rational divide(Rational other) {
        // `r₁ / r₂ = (a₁ * b₂) / (a₂ * b₁)` if `a₂` is not zero.
        int numerator = this.getNumerator() * other.getDenominator() ;
        int den = getDenominator() * other.getNumerator();
        return new Rational(numerator,den);
    }

    Rational abs() {
        // `r₁ * r₂ = (a₁ * a₂) / (b₁ * b₂)`.
        return new Rational(Math.abs(getNumerator()),Math.abs(getDenominator()));
    }

    Rational pow(int power) {
        if(power < 0) {
            // negative integer power `n` is `r^n = (b^m)/(a^m)`, where `m = |n|`.
            int powerAbs = Math.abs(power);
            return new Rational((int)Math.pow(getDenominator(),powerAbs),(int)Math.pow(getNumerator(),powerAbs));
        } else {
            // non-negative integer power `n` is `r^n = (a^n)/(b^n)`.
            return new Rational((int)Math.pow(getNumerator(),power),(int)Math.pow(getDenominator(),power));
        }

    }

    private Map.Entry<Integer,Integer> reduce(int numerator, int denominator) {
        int absNum = Math.abs(numerator);
        int absDen = Math.abs(denominator);
        if(numerator == 0) return Map.entry(0, 1);
        int small = Math.min(absNum, absDen);
        int big = Math.max(absNum, absDen);
        int i = small;
        while(i >0 && (big % i != 0 || small % i != 0)) {
            i--;
        }
        return cleanNegative(i > 0 ? numerator/i:numerator,i > 0 ? denominator/i:denominator);
    }

    private Map.Entry<Integer,Integer> cleanNegative(int numerator, int denominator) {
        if(denominator < 0) {
            return Map.entry(numerator*-1,denominator*-1);
        }
        return Map.entry(numerator,denominator);
    }


    double exp(double exponent) {
        // `x^(a/b) = root(x^a, b)`, where `root(p, q)` is the `q`th root of `p`.
        int a = getNumerator();
        int b = getDenominator();
        if (exponent == 0 && a > 0) {
                return 0.0;
        }

        // Step 1: integer exponentiation
        double p = Math.pow(exponent, a);

        // Step 2: compute b-th root
        if (p < 0 && b % 2 == 1) {
            // odd root of negative -> real
            return -Math.pow(Math.abs(p), 1.0 / b);
        } else {
            return Math.pow(p, 1.0 / b);
        }
    }

    @Override
    public String toString() {
        return String.format("%d/%d", this.getNumerator(), this.getDenominator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rational other) {
            return this.getNumerator() == other.getNumerator()
                    && this.getDenominator() == other.getDenominator();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getNumerator(), this.getDenominator());
    }
}
