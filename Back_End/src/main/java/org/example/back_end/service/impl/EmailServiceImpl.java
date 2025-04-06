package org.example.back_end.service.impl;

import jakarta.mail.internet.MimeMessage;
import org.example.back_end.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String sender = "priyadarshanissp@gmail.com";


    private String registeredAlert(String userName) {
        String dateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format("""
                <html>
                  <body style="font-family: sans-serif;">
                    <h2>Welcome to Our App!</h2>
                    <p>Hi <strong>%s</strong>,</p>
                    <p>Thank you for registering with us. Your account has been successfully created.</p>
                    <p><strong>Registration Date & Time:</strong> %s</p>
                    <hr>
                    <p style="font-size: 12px; color: gray;">This is an automated message. Please do not reply to this email.</p>
                  </body>
                </html>
                """, userName, dateTime);
    }


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
}
