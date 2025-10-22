import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

import static java.time.DayOfWeek.*;

public class SwiftScheduling {
    private static final Set<DayOfWeek> FIRST_HALF_WEEK = Set.of(MONDAY, TUESDAY, WEDNESDAY);
    private static final Map<DayOfWeek,Integer> DIFF_TO_FRIDAY = Map.of(MONDAY,4,TUESDAY,3,WEDNESDAY,2);
    private static final Map<DayOfWeek,Integer> DIFF_TO_SUNDAY = Map.of(THURSDAY,3, FRIDAY,2);
    private static final Map<DayOfWeek,Integer> ADD_TO_WORKDAY = Map.of(MONDAY,0,TUESDAY,0,
            WEDNESDAY,0,THURSDAY,0, FRIDAY,0,SATURDAY,2,SUNDAY,1);
    private static final Map<DayOfWeek,Integer> MINUS_TO_WORKDAY = Map.of(MONDAY,0,TUESDAY,0,
            WEDNESDAY,0,THURSDAY,0, FRIDAY,0,SATURDAY,-1,SUNDAY,-2);

    public static LocalDateTime convertToDeliveryDate(LocalDateTime meetingStart, String description) {
        return switch (description) {
            case "NOW" -> getNow(meetingStart);
            case "ASAP" -> getAsap(meetingStart);
            case "EOW" -> getEow(meetingStart);
            default -> getVariable(meetingStart,description);
        };
    }

    public static LocalDateTime getNow(LocalDateTime meetingStart) {
        return meetingStart.plusHours(2);
    }

    public static LocalDateTime getAsap(LocalDateTime meetingStart) {
        if(meetingStart.getHour() < 13) {
            return meetingStart.withHour(17).truncatedTo(ChronoUnit.HOURS);
        } else {
            return meetingStart.plusDays(1).withHour(13).truncatedTo(ChronoUnit.HOURS);
        }
    }

    public static LocalDateTime getEow(LocalDateTime meetingStart) {
        DayOfWeek dayOfWeek = meetingStart.getDayOfWeek();
        if(FIRST_HALF_WEEK.contains(dayOfWeek)) {
           return meetingStart.with(FRIDAY).withHour(17).truncatedTo(ChronoUnit.HOURS);
        } else {
            return meetingStart.with(SUNDAY).withHour(20).truncatedTo(ChronoUnit.HOURS);
        }
    }

    public static LocalDateTime getVariable(LocalDateTime meetingStart, String description) {
        if(description.charAt(0) == 'Q') {
            return getNQ(meetingStart,Integer.parseInt(description.substring(1)));
        }
        return getNMonth(meetingStart,Integer.parseInt(description.substring(0,description.length()-1)));
    }

    public static LocalDateTime getNMonth(LocalDateTime meetingStart, int n) {
        LocalDateTime newDate = meetingStart.withMonth(n).withDayOfMonth(1).withHour(8).truncatedTo(ChronoUnit.HOURS);
        if(newDate.isBefore(meetingStart)) {
            newDate = newDate.plusYears(1);
        }
        return newDate.plusDays(ADD_TO_WORKDAY.get(newDate.getDayOfWeek()));
    }

    public static LocalDateTime getNQ(LocalDateTime meetingStart, int n) {
        int month = (n*3);
        LocalDateTime newDate = meetingStart.withMonth(month);
        newDate = newDate.withDayOfMonth(newDate.getMonth().length(false)).withHour(8).truncatedTo(ChronoUnit.HOURS);
        if(newDate.isBefore(meetingStart)) {
            newDate = newDate.plusYears(1);
        }
        return newDate.plusDays(MINUS_TO_WORKDAY.get(newDate.getDayOfWeek()));
    }
}
