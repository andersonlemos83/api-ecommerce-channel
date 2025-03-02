package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.email;

import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import lombok.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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
    private String attachmentBase64;
    private String fileName;

    public List<String> generateEmailTo() {
        if (emailToRegex == null || "".equalsIgnoreCase(emailToRegex)) {
            return emptyList();
        } else {
            return asList(emailToRegex.split(";"))
                    .stream()
                    .sorted(Comparator.naturalOrder())
                    .toList();
        }
    }

    public List<String> generateEmailFrom() {
        if (emailFromRegex == null || "".equalsIgnoreCase(emailFromRegex)) {
            return emptyList();
        } else {
            return asList(emailFromRegex.split(";"))
                    .stream()
                    .sorted(Comparator.naturalOrder())
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
}