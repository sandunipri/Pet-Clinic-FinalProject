package org.example.back_end.dto.formDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDetailsDTO {
    private Date date;
    private String time;
    private String reason;
    private String phone;
    private String petId;
    private String vetId;

}
