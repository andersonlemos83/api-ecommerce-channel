package br.com.alc.ecommerce.channel.infrastructure.dto.customerinvoice;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInvoiceRequestDto implements Serializable {

    private String emailTo;
    private String emailSubject;
    private String emailBody;
    private String invoiceBase64;
    private String fileName;

}