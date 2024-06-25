package airlinecompany2server.airlinecompany2server.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Booking extends BaseEntity {
    @NotNull(message = "Validation: Full name is required")
    @NotEmpty(message = "Validation: Full name cannot be empty")
    private String fullName;

    @NotNull(message = "Validation: Email name is required")
    @NotEmpty(message = "Validation: Email name cannot be empty")
    @Email(message = "Validation: Email should be valid")
    private String email;

    @NotNull(message = "Validation: Birth date is required")
    private LocalDateTime birthDate;

    @NotNull(message = "Validation: Passport ID is required")
    @NotEmpty(message = "Validation: Passport ID cannot be empty")
    private String passportId;

    public Booking(String fullName, String email, LocalDateTime birthDate, String passportId) {
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.passportId = passportId;
    }
    
    public String getFullName() {
        return fullName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public String getPassportId() {
        return passportId;
    }

    @Override
    public void validate() {
        super.validate();

        LocalDateTime now = LocalDateTime.now();

        if (birthDate.isAfter(now)) {
            throw new IllegalArgumentException("Validation: Birth date must be in the past.");
        }
    }

}
