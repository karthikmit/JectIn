package com.jectin.service;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import com.jectin.db.ApplicationProperties;

public class MailSendHandler {

    // TODO Keep senderId and password in configuration file.

    private final String senderId;
    private final String password;
    private static MailSendHandler handler;

    private MailSendHandler(String senderId, String password) {
        this.senderId = senderId;
        this.password = password;
    }

    public static MailSendHandler getMailSendHandler() {

        if(handler == null) {
            handler = new MailSendHandler(ApplicationProperties.MAILUSERNAME, ApplicationProperties.MAILPASSWORD);
        }

        return handler;
    }

    public void sendMail(String receipientId, String subject, String messageBody) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", ApplicationProperties.SMTP_HOST);
        props.put("mail.smtp.port", ApplicationProperties.SMTP_PORT);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(senderId, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderId));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receipientId));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getHelloMailMessageBody(String name, String activcode) {
        return MailMessagesHelper.getSignupHelloMessage(name, activcode);
    }

    public void sendHelloMailTo(String name, String receipientId, String activCode) {

        String helloMailMessageBody = getHelloMailMessageBody(name, activCode);
        System.out.println(helloMailMessageBody);
        sendMail(receipientId, getHelloMailSubject(), helloMailMessageBody);
    }

    private String getHelloMailSubject() {
        return "Welcome to Ject-In";
    }
}