package com.jectin.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jectin.service.MailSendHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mail")
public class MailController {

	@RequestMapping(value="/contact", method=RequestMethod.POST)
	public @ResponseBody String sendHelloWorld(@RequestParam(value="fromId") String fromId,
                                               @RequestParam(value="toId") String receipientId,
												@RequestParam(value="subject") String subject,
												@RequestParam(value="message") String messageBody) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ject.in.apps@gmail.com", "KA**11an");
			}
		  });
		
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromId));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(receipientId));
			message.setSubject(subject);
			message.setText(messageBody);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		return "Mail successfully sent";
	}

    private void SendHelloMessage() {

    }
}
