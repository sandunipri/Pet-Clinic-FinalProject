package org.example.back_end.service.impl;

import jakarta.mail.internet.MimeMessage;
import org.example.back_end.dto.AppointmentDTO;
import org.example.back_end.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String sender = "vetmedpetclinic2025@gmail.com";

    @Override
    public void sendRegisteredEmail(String name, String email, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(registeredAlert(name), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String registeredAlert(String userName) {
        String dateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format("""
                <html>
                  <body style="font-family: Arial, sans-serif; background-color: #f5f9fc; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; border-radius: 10px; padding: 25px; box-shadow: 0 2px 10px rgba(0,0,0,0.05);">
                      <div style="text-align: center; margin-bottom: 20px;">
                        <img src="https://example.com/vetmed-logo.png" alt="VET MED Pet Clinic" style="max-width: 180px; height: auto;">
                        <h2 style="color: #2a6496; margin-top: 15px;">Welcome to VET MED Pet Clinic!</h2>
                      </div>
                     \s
                      <p style="font-size: 16px;">Dear <strong style="color: #2a6496;">%s</strong>,</p>
                     \s
                      <p style="font-size: 16px; line-height: 1.6;">Thank you for registering with VET MED Pet Clinic. We're excited to care for your furry family members!</p>
                     \s
                      <div style="background-color: #f0f7fd; padding: 15px; border-left: 4px solid #2a6496; margin: 20px 0; border-radius: 0 4px 4px 0;">
                        <p style="margin: 0;"><strong>Registration Details:</strong></p>
                        <p style="margin: 5px 0 0 15px;">• <strong>Date & Time:</strong> %s</p>
                        <p style="margin: 5px 0 0 15px;">• <strong>Next Steps:</strong> Book your pet's first appointment online or call us at (555) 123-4567</p>
                      </div>
                     \s
                      <div style="text-align: center; margin: 25px 0;">
                        <a href="https://vetmed.example.com/book-appointment" style="background-color: #2a6496; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">Book First Appointment</a>
                      </div>
                     \s
                      <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 20px 0;">
                     \s
                      <p style="font-size: 12px; color: #777; line-height: 1.5;">
                        <strong>VET MED Pet Clinic</strong><br>
                        123 Animal Care Way, Petville, PV 12345<br>
                        Phone: (555) 123-4567 | Emergency: (555) 987-6543<br>
                        Hours: Mon-Fri 8AM-7PM, Sat 9AM-4PM<br><br>
                        This is an automated message. For questions, please contact our office directly.
                      </p>
                    </div>
                  </body>
                </html>
                """, userName, dateTime);
    }

    @Override
    public void sendAppointmentEmail(String name, String email, String appointmentConfirmation , AppointmentDTO appointmentDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject(appointmentConfirmation);
            helper.setText(appointmentAlert(name,appointmentDTO), true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private String appointmentAlert(String name, AppointmentDTO appointmentDTO) {
        String dateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format("""
      <html>
        <body style="font-family: Arial, sans-serif; background-color: #f5f9fc; padding: 20px;">
          <div style="max-width: 600px; margin: 0 auto; background-color: white; border-radius: 10px; padding: 25px; box-shadow: 0 2px 10px rgba(0,0,0,0.05);">
            <div style="text-align: center; margin-bottom: 20px;">
              <img src="https://example.com/vetmed-logo.png" alt="VET MED Pet Clinic" style="max-width: 180px; height: auto;">
              <h2 style="color: #2a6496; margin-top: 15px;">Appointment Confirmation</h2>
            </div>
            <p style="font-size: 16px;">Dear <strong style="color: #2a6496;">%s</strong>,</p>
            <p style="font-size: 16px; line-height: 1.6;">Thank you for scheduling an appointment with VET MED Pet Clinic. We look forward to seeing you and your pet!</p>
            <div style="background-color: #f0f7fd; padding: 15px; border-left: 4px solid #2a6496; margin: 20px 0; border-radius: 0 4px 4px 0;">
              <p style="margin: 0;"><strong>Appointment Details:</strong></p>
              <p style="margin: 5px 0 0 15px;">• <strong>Date & Time:</strong> %s</p>
              <p style="margin: 5px 0 0 15px;">• <strong>Pet Name:</strong> %s</p>
              <p style="margin: 5px 0 0 15px;">• <strong>Veterinarian:</strong> Dr. %s</p>
              <p style="margin: 5px 0 0 15px;">• <strong>Service:</strong> %s</p>
            </div>
            <p style="font-size: 15px; margin-bottom: 20px;">
              <strong>Reminder:</strong> Please arrive 10 minutes early for paperwork and bring any medical records.
            </p>
            <div style="text-align: center; margin: 25px 0;">
              <a href="https://vetmed.example.com/appointment-details/%s" style="background-color: #2a6496; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">View Appointment Details</a>
              &nbsp;&nbsp;
              <a href="https://vetmed.example.com/reschedule/%s" style="background-color: #e9f0f7; color: #2a6496; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block; border: 1px solid #2a6496;">Reschedule</a>
            </div>
            <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 20px 0;">
            <p style="font-size: 12px; color: #777; line-height: 1.5;">
              <strong>VET MED Pet Clinic</strong><br>
              123 Animal Care Way, Petville, PV 12345<br>
              Phone: (555) 123-4567 | Emergency: (555) 987-6543<br>
              Hours: Mon-Fri 8AM-7PM, Sat 9AM-4PM<br><br>
              This is an automated message. For questions, please contact our office directly.
            </p>
          </div>
        </body>
      </html>
      """,
                name,                     // %s (1) - Client name
                dateTime,                 // %s (2) - Appointment date/time
                appointmentDTO.getPet().getPetName(),         // %s (3) - Pet name
                appointmentDTO.getVeterinarian().getFirstName(), // %s (4) - Vet's first name
                appointmentDTO.getVeterinarian().getSpecialty(),               // %s (5) - Service type
                appointmentDTO.getId(),                       // %s (6) - For "View Details" URL
                appointmentDTO.getId()                        // %s (7) - For "Reschedule" URL
        );


    }


}
