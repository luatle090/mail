package github.hluat.springcore.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.core.io.FileSystemResource;

import lombok.NonNull;

public interface MailService {
   void sendWithImageInline(String from, String to, String subject,
         String body, @NonNull List<FileSystemResource> fileImages) throws MessagingException, UnsupportedEncodingException;
}
