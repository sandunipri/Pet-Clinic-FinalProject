package org.example.back_end.dto;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.entity.Pet;
import org.example.back_end.entity.User;
import org.example.back_end.entity.Veterinarian;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDTO {
    private int id;
    private Date date;
    private String time;
    private String reason;
    private Pet pet;
    private Veterinarian veterinarian;
    private User user;

}
