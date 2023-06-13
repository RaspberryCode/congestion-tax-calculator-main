package congestion.calculator.domain.model.datesaggregator;

import java.time.LocalTime;

record TimeRange(LocalTime start, LocalTime end) {
    boolean isInRange(LocalTime date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
