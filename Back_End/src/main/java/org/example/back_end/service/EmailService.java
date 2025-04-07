package org.example.back_end.service;

import org.apache.commons.lang3.ClassUtils;
import org.example.back_end.dto.AppointmentDTO;

public interface EmailService {

    void sendRegisteredEmail(String name, String email, String subject);

    void sendAppointmentEmail(String name, String email, String appointmentConfirmation, AppointmentDTO appointmentDTO);
}


