package org.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private String paymentDate;
    private String paymentMethod;
    private double paymentAmount;
    private String paymentStatus;

    @ManyToOne
    private Appointments appointments;
}
