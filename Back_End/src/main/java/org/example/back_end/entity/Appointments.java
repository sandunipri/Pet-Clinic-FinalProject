package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointments {

    @Id
    private int id;
    private LocalDateTime appointmentDate;
    private String reason;
    private String status;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "petId")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id", referencedColumnName = "id")
    private Veterinarian veterinarian;

}
