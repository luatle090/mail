package github.hluat.springcore.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @NonNull
    private JavaMailSender emailSender;

    @Override
    public void sendWithImageInline(String from, String to, String subject, String body, @NonNull List<FileSystemResource> fileImages)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(from, "github-luatle090");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        for(var fileImage : fileImages) {
            helper.addInline(fileImage.getPath(), fileImage);
        }
        emailSender.send(message);
    }
}
