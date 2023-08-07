package com.dudis.foodorders.services;

import com.dudis.foodorders.api.controllers.RegistrationController;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;

public interface EmailSender {

    void send(String to, String mailMessage);

    String CONFIRMATION_EMAIL = """
        <html>
        <body>
        <p>Dear %s,</p>
                
        <p>Thank you for registering! To complete the registration process, please click on the following confirmation link:</p>
        
        <p><a href="%s">Confirm Email</a> Link will expire in 60 minutes.</p>
                
        <p>After clicking the link, your account will be activated, and you will be able to access our services.</p>
                
        <p>Best regards,<p>
        <br>
        Food Orders Administration
        </body>
        </html>
        """;

    default String buildConfirmationEmail(String token, RegistrationRequest request) {
        return String.format(
            CONFIRMATION_EMAIL,
            request.getUserName(),
            "http://localhost:8150/food-orders" + RegistrationController.REGISTRATION_CONFIRM + "?token="+ token
        );
    }
}
