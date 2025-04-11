package org.example.back_end.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private int id;
    private Date date;
    private String time;
    private String reason;
    private PetDTO pet;
    private VeterinarianDTO veterinarian;
    private UserDTO user;
}
