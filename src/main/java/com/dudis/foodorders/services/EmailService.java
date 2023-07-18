package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.exception.MailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService implements EmailSender {


    public static final String ENCODING = "UTF-8";
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void send(String to, String mailMessage) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, ENCODING);
            helper.setText(mailMessage,true);
            helper.setTo(to);
            helper.setSubject("Confirm Registration");
            helper.setFrom("admin@foodorders.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send Email", e);
            throw new MailException("Failed to send Email");
        }
    }
}
