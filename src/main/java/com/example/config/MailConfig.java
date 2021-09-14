//package com.example.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
///**
// * @author D
// */
//
//@Slf4j
//@Configuration
//public class MailConfig {
//  @Value("${spring.mail.host}")
//  private String mailHost;
//
//  @Value("${spring.mail.port}")
//  private int mailPort;
//
//  @Value("${spring.mail.username}")
//  private String username;
//
//  @Value("${spring.mail.password}")
//  private String password;
//
//  @Bean
//  public JavaMailSender getJavaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost(mailHost);
//    mailSender.setPort(mailPort);
//    mailSender.setUsername(username);
//    mailSender.setPassword(password);
//
//    log.info("STMP mail: " + mailHost + "- Port: " + mailPort + "- Username: " + username);
//
//    Properties properties = mailSender.getJavaMailProperties();
//    properties.put("mail.transport.protocol", "smtp");
//    properties.put("mail.smtp.auth", "true");
//    properties.put("mail.smtp.starttls.enable", "true");
//    properties.put("mail.debug", "true");
//
//    return mailSender;
//  }
//}
