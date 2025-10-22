import java.util.Objects;

class Clock {
    int hours;
    int minutes;
    Clock(int hours, int minutes) {
        this.hours=hours;
        this.minutes=minutes;
        normalize();
    }

    void add(int minutes) {
        this.minutes+=minutes;
        normalize();
    }

    void normalize() {
        int totalMinutes = this.hours * 60 + this.minutes;
        // Normalize totalMinutes within a 24-hour range
        totalMinutes = ((totalMinutes % (24 * 60)) + (24 * 60)) % (24 * 60);

        this.hours = totalMinutes / 60;
        this.minutes = totalMinutes % 60;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d",this.hours,this.minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clock clock = (Clock) o;
        return hours == clock.hours && minutes == clock.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }
}