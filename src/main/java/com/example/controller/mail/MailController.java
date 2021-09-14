package com.example.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author D
 */
@Controller
public class MailController {

  @Autowired
  public JavaMailSender emailSender;

  @ResponseBody
  @RequestMapping("/send-mail")
  public String sendSimpleMail() throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();

    boolean multipart = true;

    MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

    String htmlMsg = "<h3>Im testing send a HTML email</h3>"
            +"<img src='http://www.apache.org/images/asf_logo_wide.gif'>";

    message.setContent(htmlMsg, "text/html");

    helper.setTo("ntd0021995@gmail.com");

    helper.setSubject("Test send HTML email");
    this.emailSender.send(message);

    return "Email Sent";
  }
}