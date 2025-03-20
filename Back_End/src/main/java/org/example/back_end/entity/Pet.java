package org.example.back_end.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;
    private String petName;
    private String species;
    private String breed;
    private String age ;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;

}
