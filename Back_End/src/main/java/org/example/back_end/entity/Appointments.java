package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private String time;
    private String reason;

    @OneToMany(mappedBy = "appointments")
    private List<Payment> paymentsLists;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Veterinarian veterinarian;

    @ManyToOne
    private User user;


}
