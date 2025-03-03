package br.com.alc.ecommerce.channel.infrastructure.persistence.document;

import br.com.alc.ecommerce.channel.core.domain.order.OrderStatus;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class OrderDocument {

    @Id
    private String id;

    @Field("orderRequest")
    private OrderRequestDto orderRequest;

    @Field("invoiceKey")
    private String invoiceKey;

    @Field("invoiceNumber")
    private String invoiceNumber;

    @Field("issuanceDate")
    private LocalDateTime issuanceDate;

    @Field("invoiceBase64")
    private String invoiceBase64;

    @Field("status")
    private OrderStatus status;

    @Field("errorReason")
    private String errorReason;

    @Field("createdDate")
    private LocalDateTime createdDate;

    @Field("updatedDate")
    private LocalDateTime updatedDate;

}