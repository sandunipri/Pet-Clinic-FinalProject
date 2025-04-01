package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Table(name = "vet")
@Entity
public class Veterinarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String licenseNo;
    private String specialty;
    private String YOEeperience;
    private String bio;
    private String profileImage;

    @OneToMany(mappedBy = "veterinarian")
    private List<MedicalReport> medicalReportList;

    @OneToMany(mappedBy = "veterinarian")
    private List<Appointments> appointmentsList;
}
