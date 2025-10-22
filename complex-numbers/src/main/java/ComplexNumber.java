class ComplexNumber {
    double real;
    double imaginary;
    double i;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    double getReal() {
        return this.real;
    }

    double getImaginary() {
        return this.imaginary;
    }

    double abs() {
        return Math.sqrt(Math.pow(this.real,2)+Math.pow(this.imaginary,2));
    }

    ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real+other.getReal()
                ,this.imaginary+other.getImaginary());
    }

    ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.real-other.getReal()
                ,this.imaginary-other.getImaginary());
    }

    ComplexNumber multiply(ComplexNumber other) {
        double ac = this.real*other.getReal();
        double bd = this.imaginary*other.getImaginary();
        double bc = other.getReal()*this.getImaginary();
        double ad = this.getReal()*other.getImaginary();
        return new ComplexNumber(ac-bd
                ,bc+ad);
    }

    ComplexNumber divide(ComplexNumber other) {
        double acPlusBd = (this.real*other.getReal())+(this.imaginary*other.getImaginary());
        double cd2 = Math.pow(other.getReal(),2)+Math.pow(other.getImaginary(),2);
        double bcMinusAd = (this.imaginary*other.getReal())-(this.getReal()*other.getImaginary());
        return new ComplexNumber(acPlusBd / cd2
                ,bcMinusAd/cd2);
    }

    ComplexNumber conjugate() {
        return new ComplexNumber(this.real, -this.imaginary);
    }

    ComplexNumber exponentialOf() {
        double expReal = Math.exp(this.real); // e^a
        double realPart = expReal * Math.cos(this.imaginary); // e^a * cos(b)
        double imagPart = expReal * Math.sin(this.imaginary); // e^a * sin(b)
        return new ComplexNumber(realPart, imagPart);
    }

}