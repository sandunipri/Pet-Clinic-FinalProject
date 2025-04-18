package org.example.back_end.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;
    private String petName;
    private String species;
    private double weight;
    private String breed;
    private int age ;
    private String gender;
    private String petImage;

    @OneToMany(mappedBy = "pet")
    private List<MedicalReport> medicalReportList;

    @OneToMany(mappedBy = "pet")
    private List<Appointments> appointmentsList;

    @ManyToOne
    private User user;

}
