import java.time.LocalDate;

class Leap {

    boolean isLeapYear(int year) {
        LocalDate d = LocalDate.of(year,1,1);
        return d.isLeapYear();
    }

}
