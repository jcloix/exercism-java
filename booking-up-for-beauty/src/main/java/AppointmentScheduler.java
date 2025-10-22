import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Locale;

class AppointmentScheduler {
    public LocalDateTime schedule(String appointmentDateDescription) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return LocalDateTime.parse(appointmentDateDescription, parser);
    }

    public boolean hasPassed(LocalDateTime appointmentDate) {
        return appointmentDate.isBefore(LocalDateTime.now());
    }

    public boolean isAfternoonAppointment(LocalDateTime appointmentDate) {
        return appointmentDate.getHour() >= 12 && appointmentDate.getHour() < 18;
    }

    public String getDescription(LocalDateTime appointmentDate) {
        LocalDateTime d = appointmentDate;
        return String.format("You have an appointment on %s, %s %d, %d, at %d:%02d %s.",
                d.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                d.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                d.getDayOfMonth(), d.getYear(),
                d.get(ChronoField.CLOCK_HOUR_OF_AMPM), d.getMinute(),
                d.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM");
    }

    public LocalDate getAnniversaryDate() {
        return LocalDate.of(LocalDate.now().getYear(),9,15);
    }
}
