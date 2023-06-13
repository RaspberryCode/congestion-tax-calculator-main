package congestion.calculator.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "schedule_range")
public final class TollFeeScheduleRange {
    @Id
    private int id;
    @Column(name = "range_start")
    private LocalTime start;
    @Column(name = "range_end")
    private LocalTime end;
    private int fee;

    public TollFeeScheduleRange() {
    }

    public boolean isInRange(LocalTime time) {
        return !time.isBefore(start) && !time.isAfter(end);
    }

    public int getFee() {
        return fee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TollFeeScheduleRange) obj;
        return this.id == that.id && Objects.equals(this.start, that.start) && Objects.equals(this.end,
                that.end) && this.fee == that.fee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, fee);
    }
}
