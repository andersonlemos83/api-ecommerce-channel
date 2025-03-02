package br.com.alc.ecommerce.channel.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.email.EmailDataTable;
import com.icegreen.greenmail.util.GreenMail;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static jakarta.mail.Part.ATTACHMENT;
import static org.junit.Assert.assertEquals;

@Component
@AllArgsConstructor
public class EmailVerifier {

    private final GreenMail greenMail;

    public void verify(List<EmailDataTable> expecteds) throws Exception {
        List<MimeMessage> returneds = Arrays.stream(greenMail.getReceivedMessages()).toList();

        assertEquals("Should return the expected number of e-mails.", expecteds.size(), returneds.size());

        for (int i = 0; i < expecteds.size(); i++) {
            EmailDataTable expected = expecteds.get(i);
            MimeMessage returned = returneds.get(i);

            verify(expected, returned);
        }
    }

    private void verify(EmailDataTable expected, MimeMessage returned) throws MessagingException, IOException {
        assertEquals(expected.generateEmailTo(), generateEmailToReturned(returned));
        assertEquals(expected.generateEmailFrom(), generateEmailFromReturned(returned));
        assertEquals(expected.getEmailSubject(), returned.getSubject());
        assertEquals(expected.getEmailBody(), generateEmailBody(returned));
    }

    private List<String> generateEmailToReturned(MimeMessage returned) throws MessagingException {
        return Arrays.stream(returned.getAllRecipients())
                .map(Address::toString)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private List<String> generateEmailFromReturned(MimeMessage returned) throws MessagingException {
        return Arrays.stream(returned.getFrom())
                .map(Address::toString)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private String generateEmailBody(MimeMessage message) throws MessagingException, IOException {
        Object content = message.getContent();
        if (content instanceof String text) {
            return text;
        }
        if (content instanceof MimeMultipart mimeMultipart) {
            return generateText(mimeMultipart);
        }
        return null;
    }

    private String generateText(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                continue;
            }
            if (bodyPart.isMimeType("text/plain")) {
                return bodyPart.getContent().toString();
            }
            if (bodyPart.isMimeType("text/html")) {
                result.append(bodyPart.getContent().toString());
            }
            if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(generateText((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}