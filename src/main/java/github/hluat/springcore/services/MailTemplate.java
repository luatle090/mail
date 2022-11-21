package github.hluat.springcore.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import github.hluat.springcore.config.ConfigurationProper;
import github.hluat.springcore.dto.MailDTO;
import github.hluat.springcore.utils.ExcelReader;
import github.hluat.springcore.utils.ExcelWriter;
import github.hluat.springcore.utils.WriteRow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MailTemplate {

    @NonNull
    private final ConfigurationProper config;

    @Autowired
    // @Qualifier("mailWorldCup")
    private final MailSendStrategy mailSend;

    private static final Logger logger = LoggerFactory.getLogger(MailTemplate.class);

    // abstract void readResource();

    public final void processSendMail(String input, String output) throws IOException {
        int countSuccess = 0;
        int countFailed = 0;
        List<MailDTO> mailSendFailed = new ArrayList<>();
        TimeUnit time = TimeUnit.SECONDS;

        List<MailDTO> list = ExcelReader.readData(input, MailDTO.class);

        for (var mail : list) {
            try {
                if (!EmailValidator.getInstance().isValid(mail.getAddress())) {
                    logger.info("email invalid {}", mail.getAddress());
                    mailSendFailed.add(mail);
                    continue;
                }

                mailSend.send(mail);
                countSuccess++;
                logger.info("has sent a email {}", mail.getAddress());

                // sleep after full batch size
                if (countSuccess % config.getBatchSize() == 0) {
                    logger.info("time to rest {}s ", config.getSleeping());
                    time.sleep(config.getSleeping());
                }
            } catch (MessagingException | UnsupportedEncodingException messagingException) {
                countFailed++;
                logger.error("email send has failed: {}", mail.getAddress());
                mail.setErrorMessage(messagingException.getMessage());
                mailSendFailed.add(mail);

                // sleep after full batch size
                if ((countFailed + countSuccess) % config.getBatchSize() == 0) {
                    try {
                        logger.info("time to rest {}s ", config.getSleeping());
                        time.sleep(config.getSleeping());
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }

        // write output
        ExcelWriter.writeData(output, mailSendFailed, new WriteRow<MailDTO>() {

            @Override
            public void writeRow(MailDTO mail, Row row) {
                row.createCell(0).setCellValue(mail.getId()); 
                row.createCell(1).setCellValue(mail.getName());
                row.createCell(2).setCellValue(mail.getAddress());
                row.createCell(3).setCellValue(mail.getErrorMessage());
            }
        });
        logger.info(" send success: {} - send failed: {}", countSuccess, countFailed);
    }
}