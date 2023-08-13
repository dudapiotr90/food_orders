package com.dudis.foodorders.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public class DecoratedMimeMessageHelper extends MimeMessageHelper {

    public DecoratedMimeMessageHelper(MimeMessage mimeMessage) throws MessagingException {
        super(mimeMessage);
    }

    @Override
    public void setText(String subject) throws MessagingException {
        throw new MessagingException();
    }
}
