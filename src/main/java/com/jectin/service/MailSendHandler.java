package com.jectin.service;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

public class MailSendHandler {

    // TODO Keep senderId and password in configuration file.

    private final String senderId;
    private final String password;
    private static MailSendHandler handler;
    private String directoryName = "/tmp";

    private MailSendHandler(String senderId, String password) {
        this.senderId = senderId;
        this.password = password;
    }

    public static MailSendHandler getMailSendHandler() {

        if(handler == null) {
            String username = "ject.in.apps@gmail.com";
            String password = "KA**11an";
            handler = new MailSendHandler(username, password);
        }

        return handler;
    }

    public void sendMail(String receipientId, String subject, String messageBody) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

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
        return "Welcome to JectIn";
    }

    private String getResourcesFolder() {
        String classpath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        File dir = new File(classpath);

        if(dir.isDirectory()) {
            File parentDirectory = dir.getParentFile();

            if(parentDirectory.isDirectory()) {
                String rootDir = parentDirectory.getParent();

                System.out.println(rootDir);
                return rootDir + "/resources";
            }
        }

        return "";
    }
}