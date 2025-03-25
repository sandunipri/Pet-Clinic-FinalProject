package org.example.back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.entity.Appointments;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String paymentDate;
    private String paymentMethod;
    private double paymentAmount;
    private String paymentStatus;
    private Appointments appointments;
}
