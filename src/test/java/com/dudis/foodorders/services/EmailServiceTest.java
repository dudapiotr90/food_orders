package com.dudis.foodorders.services;

import com.dudis.foodorders.utils.DecoratedMimeMessage;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender mailSender;

    @Test
    void sendWorksCorrectly() {
        // Given
        String receiver = "some@mail";
        String someMailMessage = "some mail message";
        MimeMessage someMimeMessage = mock(DecoratedMimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(someMimeMessage);
        doNothing().when(mailSender).send(any(MimeMessage.class));

        // When
        emailService.send(receiver, someMailMessage);

        // Then
        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

}