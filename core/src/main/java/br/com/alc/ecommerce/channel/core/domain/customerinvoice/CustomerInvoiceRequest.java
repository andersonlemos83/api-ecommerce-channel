package br.com.alc.ecommerce.channel.core.domain.customerinvoice;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoiceRequest implements Serializable {

    private String emailTo;
    private String emailFrom;
    private String emailSubject;
    private String emailBody;
    private String attachmentBase64;
    private String fileName;

}