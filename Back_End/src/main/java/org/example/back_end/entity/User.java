package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String name;
    private String role;
    private String address;
    private String telNo;
    private String profileImage;

    @OneToMany(mappedBy = "user")
    private List<Pet> petList;

    @OneToMany(mappedBy = "user")
    private List<Appointments> appointmentsList;
}