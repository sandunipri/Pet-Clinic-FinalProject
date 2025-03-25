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
@ToString
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;
    private String petName;
    private String species;
    private String breed;
    private String age ;
    private String gender;

    @OneToMany(mappedBy = "pet")
    private List<MedicalReport> medicalReportList;

    @OneToMany(mappedBy = "pet")
    private List<Appointments> appointmentsList;

    @ManyToOne
    private User user;

}
