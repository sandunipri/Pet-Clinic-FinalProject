package org.example.back_end.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int petId;
    private String petName;
    private String Species;
    private String Breed;
    private String Age ;
    private String Gender;


}
