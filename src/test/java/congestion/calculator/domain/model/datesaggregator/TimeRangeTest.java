package congestion.calculator.domain.model.datesaggregator;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeRangeTest {
    private final LocalTime exampleTime = LocalTime.of(20, 12);

    @Test
    void isInRange() {
        var timeRange = new TimeRange(exampleTime, exampleTime.plusHours(1));
        assertTrue(timeRange.isInRange(exampleTime.plusMinutes(30)));
    }

    @Test
    void isNotInRange() {
        var timeRange = new TimeRange(exampleTime, exampleTime.plusHours(1));
        assertFalse(timeRange.isInRange(exampleTime.plusHours(2)));
    }
}