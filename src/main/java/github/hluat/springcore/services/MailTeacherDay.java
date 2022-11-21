package github.hluat.springcore.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import github.hluat.springcore.dto.MailDTO;
import github.hluat.springcore.utils.Constants;
import github.hluat.springcore.utils.MailService;

@Component
// @Primary
public class MailTeacherDay  implements MailSendStrategy {

    @Autowired
    private MailService mailService;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
    
    @Autowired
    private FileResource fileResource;

    @Override
    public void send(MailDTO mail) throws MessagingException, UnsupportedEncodingException {
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        Map<String, FileSystemResource> fileAttach = fileResource.getResource();
        templateModel.put("mail", mail);
        templateModel.put(Constants.IMAGE_COVER, fileAttach.get(Constants.IMAGE_COVER).getPath());
        templateModel.put(Constants.IMAGE_FOOTER, fileAttach.get(Constants.IMAGE_FOOTER).getPath());
        thymeleafContext.setVariables(templateModel);
        List<FileSystemResource> imageList = new ArrayList<>();
        imageList.add(fileAttach.get(Constants.IMAGE_COVER));
        imageList.add(fileAttach.get(Constants.IMAGE_FOOTER));
        String htmlBody = thymeleafTemplateEngine.process("mail-nha-giao.html", thymeleafContext);
        mailService.sendWithImageInline("luattestmail@gmail.com", mail.getAddress(), mail.getSubject(), htmlBody, imageList);
    }

    void readResource() {
        
    }
}
