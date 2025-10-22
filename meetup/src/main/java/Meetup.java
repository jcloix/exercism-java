import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Meetup {

    private final Set<Integer> TEENTH_SET = IntStream.rangeClosed(13,19).boxed().collect(Collectors.toSet());
    int monthOfYear;
    int year;
    Meetup(int monthOfYear, int year) {
        this.monthOfYear=monthOfYear;
        this.year=year;
    }

    LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        LocalDate date = LocalDate.of(year,monthOfYear,1);
        int diff = dayOfWeek.getValue() - date.getDayOfWeek().getValue();
        date = date.plusDays(diff);
        if(date.getMonthValue() != monthOfYear) {
            date = date.plusWeeks(1);
        }
        switch (schedule) {
            case FIRST -> {return date;}
            case SECOND -> {return date.plusWeeks(1);}
            case THIRD -> {return date.plusWeeks(2);}
            case FOURTH -> {return date.plusWeeks(3);}
            case TEENTH -> {
                while(!TEENTH_SET.contains(date.getDayOfMonth()) && date.getMonthValue() == monthOfYear) {
                    date=date.plusWeeks(1);
                }
                return date;
            }
            case LAST -> {
                while(date.plusWeeks(1).getMonthValue() == monthOfYear) {
                    date=date.plusWeeks(1);
                }
                return date;
            }
        }
        return date;
    }

}