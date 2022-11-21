package github.hluat.springcore.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import github.hluat.springcore.dto.MailDTO;

@FunctionalInterface
public interface MailSendStrategy {
    void send(MailDTO mail) throws MessagingException, UnsupportedEncodingException;
}
