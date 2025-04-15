package org.example.back_end.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private int id;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date must be today or in the future")
    private Date date;

    @NotBlank(message = "Appointment time is required")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Time must be in HH:MM format (24-hour clock)")
    private String time;

    @NotBlank(message = "Appointment reason is required")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;

    @NotNull(message = "Pet information is required")
    private PetDTO pet;

    @NotNull(message = "Veterinarian information is required")
    private VeterinarianDTO veterinarian;

    @NotNull(message = "User information is required")
    private UserDTO user;
}