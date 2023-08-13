package com.dudis.foodorders.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class DecoratedMimeMessage extends MimeMessage {


    public DecoratedMimeMessage(MimeMessage source) throws MessagingException {
        super(source);
    }
}
