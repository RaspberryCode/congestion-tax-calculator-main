package congestion.calculator.domain.model.datesaggregator;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatesIntervalAggregatorTest {

    private final DatesIntervalAggregator intervalAggregator = new DatesIntervalAggregator();
    private final LocalDateTime exampleTime = LocalDateTime.of(2023, 6, 12, 20, 12);

    @Test
    void givenDatesWith2HoursTimeDifferenceShouldReturnTwoIntervals() {

        var dates = Stream.of(exampleTime, exampleTime.plusHours(2))
                          .map(LocalDateTime::toLocalTime)
                          .toList();
        var result = intervalAggregator.aggregateInIntervals(dates);

        assertEquals(2, result.size());
    }

    @Test
    void givenDatesWith30MinutesTimeDifferenceShouldReturnSingleInterval() {
        var dates = Stream.of(exampleTime, exampleTime.plusMinutes(30))
                          .map(LocalDateTime::toLocalTime)
                          .toList();
        var result = intervalAggregator.aggregateInIntervals(dates);

        assertEquals(1, result.size());
    }
}