package airlinecompany2server.airlinecompany2server.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Discount extends BaseEntity {
    @FutureOrPresent(message = "Validation: From must be a future date or present")
    @NotNull(message = "Validation: From is required")
    private LocalDateTime from;

    @Future(message = "Validation: To must be a future date")
    @NotNull(message = "Validation: To is required")
    private LocalDateTime to;

    @DecimalMin(value = "0.0", message = "Validation: Discount value must be at least 0.0")
    @DecimalMax(value = "100.0", message = "Validation: Discount value must be at most 100.0")
    @NotNull(message = "Validation: Discount value is required")
    @Positive(message = "Validation: Discount value must be positive")
    private Float offValue;

    public Discount(LocalDateTime from, LocalDateTime to, Float offValue) {
        this.from = from;
        this.to = to;
        this.offValue = offValue;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public Float getOffValue() {
        return offValue;
    }

    @Override
    public void validate() {
        super.validate();

        LocalDateTime now = LocalDateTime.now();

        if (!from.isAfter(now) && !to.isAfter(now)) {
            throw new IllegalArgumentException("Validation: From and To times must be in the future.");
        }

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Validation: From time must be before landing time.");
        }
    }

    public boolean isActive() {
        LocalDateTime currentDate = LocalDateTime.now();
        return from.isBefore(currentDate) && to.isAfter(currentDate);
    }
}
