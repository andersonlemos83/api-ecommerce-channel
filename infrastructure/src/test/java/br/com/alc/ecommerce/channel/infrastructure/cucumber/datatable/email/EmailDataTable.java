package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.email;

import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static lombok.AccessLevel.NONE;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailDataTable implements Serializable {

    private String emailToRegex;
    private String emailFromRegex;
    private String emailSubject;
    @Getter(NONE)
    private String emailBody;
    private String attachmentBase64Regex;
    private String fileNameRegex;

    public List<String> generateEmailsTo() {
        if (emailToRegex == null || "".equalsIgnoreCase(emailToRegex)) {
            return emptyList();
        } else {
            return asList(emailToRegex.split(";"))
                    .stream()
                    .sorted(naturalOrder())
                    .toList();
        }
    }

    public List<String> generateEmailsFrom() {
        if (emailFromRegex == null || "".equalsIgnoreCase(emailFromRegex)) {
            return emptyList();
        } else {
            return asList(emailFromRegex.split(";"))
                    .stream()
                    .sorted(naturalOrder())
                    .toList();
        }
    }

    public String getEmailBody() {
        if (emailBody == null || "".equalsIgnoreCase(emailBody)) {
            return null;
        }
        if (emailBody.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResource(emailBody);
        }
        return emailBody;
    }

    public List<String> generateAttachmentsBase64() {
        if (attachmentBase64Regex == null || "".equalsIgnoreCase(attachmentBase64Regex)) {
            return emptyList();
        } else {
            return asList(attachmentBase64Regex.split(";;"))
                    .stream()
                    .map(attachment -> {
                        if (attachment.startsWith("/fixtures/")) {
                            return ResourceFixture.getContentFromResource(attachmentBase64Regex);
                        }
                        return attachment;
                    })
                    .sorted(naturalOrder())
                    .toList();
        }
    }

    public List<String> generateFileNames() {
        if (fileNameRegex == null || "".equalsIgnoreCase(fileNameRegex)) {
            return emptyList();
        } else {
            return asList(fileNameRegex.split(";;"))
                    .stream()
                    .sorted(naturalOrder())
                    .toList();
        }
    }
}