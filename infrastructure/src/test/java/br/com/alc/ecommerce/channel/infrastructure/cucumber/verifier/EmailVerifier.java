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
import java.io.InputStream;
import java.util.*;

import static jakarta.mail.Part.ATTACHMENT;
import static java.util.Comparator.naturalOrder;
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
        assertEquals(expected.generateEmailsTo(), generateEmailsToReturned(returned));
        assertEquals(expected.generateEmailsFrom(), generateEmailsFromReturned(returned));
        assertEquals(expected.getEmailSubject(), returned.getSubject());
        assertEquals(expected.getEmailBody(), generateEmailBody(returned));
        assertEquals(expected.generateAttachmentsBase64(), generateAttachmentsBase64Returned(returned));
        assertEquals(expected.generateFileNames(), generateFileNamesReturned(returned));
    }

    private List<String> generateEmailsToReturned(MimeMessage returned) throws MessagingException {
        return Arrays.stream(returned.getAllRecipients())
                .map(Address::toString)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private List<String> generateEmailsFromReturned(MimeMessage returned) throws MessagingException {
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
        return result.toString()
                .replaceAll("[\\r\\n]+", "")
                .trim();
    }

    private List<String> generateAttachmentsBase64Returned(MimeMessage message) throws MessagingException, IOException {
        Object content = message.getContent();
        List<String> fileNames = new ArrayList<>();
        if (content instanceof MimeMultipart mimeMultipart) {
            for (int i = 0; i < mimeMultipart.getCount(); i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                    byte[] byteArray = ((InputStream) bodyPart.getContent()).readAllBytes();
                    String attachmentBase64 = Base64.getEncoder().encodeToString(byteArray);
                    fileNames.add(attachmentBase64);
                }
            }
        }
        return fileNames.stream()
                .sorted(naturalOrder())
                .toList();
    }

    private List<String> generateFileNamesReturned(MimeMessage message) throws MessagingException, IOException {
        Object content = message.getContent();
        List<String> fileNames = new ArrayList<>();
        if (content instanceof MimeMultipart mimeMultipart) {
            for (int i = 0; i < mimeMultipart.getCount(); i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                    fileNames.add(bodyPart.getFileName());
                }
            }
        }
        return fileNames.stream()
                .sorted(naturalOrder())
                .toList();
    }
}