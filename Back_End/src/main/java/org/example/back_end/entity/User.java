package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


@Entity
@Table(name = "user")
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
    private String gender;
    private String nic;
    private String emergencyContact;
    private String emergencyContactName;
    private String profileImage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pet> petList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Appointments> appointmentsList;
}
