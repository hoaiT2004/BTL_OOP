package com.example.btl_oop.service.Impl;

import com.example.btl_oop.service.EmailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    static final String from = "codeptit123@gmail.com";
    static final String password = "wnhpagypqhffhwjc";
    public boolean sendEmail(String to,String title,String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);
        try{
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(from);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(title);
            msg.setSentDate(new Date());
            msg.setContent(content,"text/HTML; charset=UTF-8");
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
