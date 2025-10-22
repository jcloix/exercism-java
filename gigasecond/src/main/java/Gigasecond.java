import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gigasecond {
    LocalDateTime time;
    public Gigasecond(LocalDate moment) {
        this(moment.atStartOfDay());
    }

    public Gigasecond(LocalDateTime moment) {
        time = moment.plusSeconds(1000000000);
    }

    public LocalDateTime getDateTime() {
        return time;
    }
}
