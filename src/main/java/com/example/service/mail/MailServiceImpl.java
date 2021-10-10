package com.example.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author D
 */
@Service
public class MailServiceImpl implements MailService {

  public final String MAIL_TO = "ntd0021995@gmail.com";
  public final boolean multipartFlag = true;

  public final JavaMailSender emailSender;

  public MimeMessage message;

  public MailServiceImpl(JavaMailSender emailSender) {
    this.emailSender = emailSender;
    message = emailSender.createMimeMessage();
  }

  @Override
  public void sendMail() {
    try {
      MimeMessageHelper helper = null;

      helper = new MimeMessageHelper(message, multipartFlag, "utf-8");

      String htmlMsg = "<h3>Im testing send a HTML email</h3>"
              + "<img src='http://www.apache.org/images/asf_logo_wide.gif'>";
      message.setContent(htmlMsg, "text/html");

      helper.setTo(MAIL_TO);
      helper.setSubject("Test send HTML email");

      this.emailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendMailAttachment() {
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper = new MimeMessageHelper(message, multipartFlag, "utf-8");

      helper.setTo(MAIL_TO);
      helper.setText("Mail attachment");

      helper.setSubject("Test send HTML email");
      // Attach file
      FileSystemResource fileSystemResource = new FileSystemResource(new File("d:/image/floatingcity.png"));
      helper.addAttachment("floatingcity.png", fileSystemResource);
      this.emailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
