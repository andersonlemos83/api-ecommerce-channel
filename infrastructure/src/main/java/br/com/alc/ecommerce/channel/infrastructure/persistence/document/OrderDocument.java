package br.com.alc.ecommerce.channel.infrastructure.persistence.document;

import br.com.alc.ecommerce.channel.core.domain.order.OrderStatus;
import br.com.alc.ecommerce.channel.infrastructure.dto.order.OrderRequestDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private OrderRequestDto orderRequest;
    private String invoiceKey;
    private String invoiceNumber;
    private LocalDateTime issuanceDate;
    private String invoiceBase64;

    private OrderStatus status;
    private String errorReason;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedDate;

}