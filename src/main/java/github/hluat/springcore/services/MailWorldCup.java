package github.hluat.springcore.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import github.hluat.springcore.dto.MailDTO;
import github.hluat.springcore.utils.Constants;
import github.hluat.springcore.utils.MailService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Primary
public class MailWorldCup implements MailSendStrategy {

    @NonNull
    private MailService mailService;

    @NonNull
    private SpringTemplateEngine thymeleafTemplateEngine;

    @NonNull
    private FileResource fileResource;

    @Override
    public void send(MailDTO mail) throws MessagingException, UnsupportedEncodingException {
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        Map<String, FileSystemResource> fileAttach = fileResource.getResource();
        templateModel.put(Constants.MAIL, mail);
        templateModel.put(Constants.IMAGE_COVER, fileAttach.get(Constants.IMAGE_COVER).getPath());
        templateModel.put(Constants.IMAGE_FOOTER, fileAttach.get(Constants.IMAGE_FOOTER).getPath());
        thymeleafContext.setVariables(templateModel);
        List<FileSystemResource> imageList = new ArrayList<>();
        imageList.add(fileAttach.get(Constants.IMAGE_COVER));
        imageList.add(fileAttach.get(Constants.IMAGE_FOOTER));
        String htmlBody = thymeleafTemplateEngine.process("world-cup.html", thymeleafContext);
        mailService.sendWithImageInline("luattestmail@gmail.com", mail.getAddress(), mail.getSubject(), htmlBody, imageList); 
    }
    
}
