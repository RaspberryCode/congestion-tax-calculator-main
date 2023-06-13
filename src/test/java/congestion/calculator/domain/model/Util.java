package congestion.calculator.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime getDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }

}
