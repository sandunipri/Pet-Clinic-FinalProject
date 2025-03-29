package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(name = "vet")
@Entity
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String specialty;
    private String profileImage;

    @OneToMany(mappedBy = "veterinarian")
    private List<MedicalReport> medicalReportList;

    @OneToMany(mappedBy = "veterinarian")
    private List<Appointments> appointmentsList;
}
