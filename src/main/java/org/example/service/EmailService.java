package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendTiffinApprovalEmail(String toEmail, String address) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("vaibhavbakal555@gmail.com");
            message.setTo(toEmail);
            message.setSubject("Tiffin Request Approved - Vaibhav Mess");
            message.setText("Your tiffin request is approved! Your tiffin will be delivered in some time on given address: " + address + "\n\nRegards,\nVaibhav Mess");
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}