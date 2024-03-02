package com.sohardh.account;

import com.sohardh.account.service.mail.MailService;
import com.sohardh.account.service.mail.parser.MailParserService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AccountStatementServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountStatementServiceApplication.class, args);
  }

  @Component
  @Slf4j
  public static class MyApplicationRunner implements ApplicationRunner {

    private final MailService mailService;
    private final MailParserService mailParserService;

    public MyApplicationRunner(@Qualifier("GmailServiceImpl") MailService mailService,
        MailParserService mailParserService) {
      this.mailService = mailService;
      this.mailParserService = mailParserService;
    }


    @Override
    public void run(ApplicationArguments args) throws GeneralSecurityException, IOException {

      var emailBody = mailService.getEmailBody();
      var statementLink = mailParserService.getStatementLink(emailBody);
      log.info(statementLink.get());
    }
  }

}
