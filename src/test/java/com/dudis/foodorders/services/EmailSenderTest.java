package com.dudis.foodorders.services;

import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import com.dudis.foodorders.utils.AccountUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class EmailSenderTest {

    private final EmailSender emailSender = spy(EmailSender.class);

    @Test
    void buildConfirmationEmailWorksCorrectly() {
        // Given
        String someToken = UUID.randomUUID().toString();
        RegistrationRequest someRegistrationRequest = AccountUtils.someRegistrationRequest();
        String someConfirmationLink = RegistrationService.REGISTRATION_LINK_FORM;
        String expected = EmailSender.CONFIRMATION_EMAIL.formatted(someRegistrationRequest.getUserName(),someConfirmationLink);
        // When
        String result = emailSender.buildConfirmationEmail(someToken, someRegistrationRequest, someConfirmationLink);

        // Then
        assertEquals(expected,result);
    }
}