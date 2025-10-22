class SpaceAge {

    private final double seconds;
    private static final double EARTH_YEAR_IN_SECONDS = 31_557_600;

    public SpaceAge(double seconds) {
        this.seconds = seconds;
    }

    public double getSeconds() {
        return seconds;
    }

    public double onEarth() {
        return seconds / EARTH_YEAR_IN_SECONDS;
    }

    public double onMercury() {
        return onEarth() / 0.2408467;
    }

    public double onVenus() {
        return onEarth() / 0.61519726;
    }

    public double onMars() {
        return onEarth() / 1.8808158;
    }

    public double onJupiter() {
        return onEarth() / 11.862615;
    }

    public double onSaturn() {
        return onEarth() / 29.447498;
    }

    public double onUranus() {
        return onEarth() / 84.016846;
    }

    public double onNeptune() {
        return onEarth() / 164.79132;
    }

}
