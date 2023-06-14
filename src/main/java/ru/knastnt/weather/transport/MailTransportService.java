package ru.knastnt.weather.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Service
public class MailTransportService implements TransportService {
    public static final String ENCODING = "UTF-8";
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;
    @Value("${mail.subject}")
    private String subject;
    @Value("${mail.body}")
    private String body;
    @Value("${mail.file-name}")
    private String fileName;

    @Override
    @Retryable
    public void sendContent(byte[] content, String address, String city) {
        log.debug("Send content byte[{}] to email: \"{}\"", content.length, address);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setRecipients(Message.RecipientType.TO, address);

            MimeMessageHelper helper = new MimeMessageHelper(message, true, ENCODING);

            helper.setSubject(subject.replace("CITY_NAME", hasText(city) ? city : "").trim());
            helper.setText(body, false);
            helper.setFrom(from);
    //        helper.addAttachment(fileName, new ByteArrayResource(content));
            helper.addAttachment(fileName, new ByteArrayDataSource(content, "application/octet-stream"));

        } catch (MessagingException e) {
            throw new RuntimeException("Exception in prepare message for sending", e);
        }

        log.info("Sending email");
        mailSender.send(message);
        log.info("Email sent");
    }

    @Recover
    void logFailAndThrowEx(Exception e, byte[] content, String address, String city) {
        log.warn("Maximum number of attempts for sending email exceeded", e);
        throw new RuntimeException("Can't send email to: \"" + address + "\"");
    }
}
