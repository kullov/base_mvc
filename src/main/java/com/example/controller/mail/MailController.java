package com.example.controller.mail;

import com.example.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;

/**
 * @author D
 */
@Controller
public class MailController {

  @Autowired
  private MailService mailService;

  @ResponseBody
  @RequestMapping("/send-mail")
  public String sendSimpleMail() throws MessagingException {
    this.mailService.sendMail();
    return "Email Sent";
  }

  @GetMapping("/send-mail-attach")
  public String sendMailAttachment() throws MessagingException {
    this.mailService.sendMailAttachment();
    return "Sender Mail attachment";
  }
}
