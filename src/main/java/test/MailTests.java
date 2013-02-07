package test;

import com.jectin.service.MailMessagesHelper;
import com.jectin.service.MailSendHandler;
import org.junit.Test;

public class MailTests {
    private final MailSendHandler mailSendHandler = MailSendHandler.getMailSendHandler();

    @Test
    public void simpleMailSendTest() {
        String receipientId = "karthik.moc@gmail.com";

        String subject = "Hello Karthik";
        String messageBody = "This is testing...";

        mailSendHandler.sendMail(receipientId, subject, messageBody);
    }

    @Test
    public void stringTemplateBasedSimpleMailTest() {
        String receipientId = "karthik.moc@gmail.com";
        String subject = "Hello Karthik";

        String message = MailMessagesHelper.getSignupHelloMessage("karthik", "1234");
        System.out.println(message);
        mailSendHandler.sendMail(receipientId, subject, message);
    }
}
