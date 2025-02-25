package br.com.alc.ecommerce.channel.infrastructure.messaging.producer.impl;

import br.com.alc.ecommerce.channel.infrastructure.dto.error.ErrorResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.alc.ecommerce.channel.infrastructure.helper.util.ObjectMapperHelper.generateJson;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.*;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class MessagingProducerImplTest {

    @InjectMocks
    private MessagingProducerImpl messagingProducer;

    @Mock
    private RabbitTemplate rabbitTemplateMock;

    @Test
    void givenThatItThrowsAnyExceptionWhenExecutingThePublishMethodWithThreeParametersThenShouldGenerateAnErrorLog() {
        String exchange = "exchange";
        String queue = "queue";
        ErrorResponseDto errorResponseDto = Instancio.create(ErrorResponseDto.class);
        Message message = buildMessage(generateJson(errorResponseDto));

        doThrow(RuntimeException.class).when(rabbitTemplateMock).convertAndSend(exchange, queue, message);

        messagingProducer.publish(exchange, queue, errorResponseDto);

        verify(rabbitTemplateMock, times(1)).convertAndSend(exchange, queue, message);
    }

    private Message buildMessage(String content) {
        return MessageBuilder
                .withBody(content.getBytes(UTF_8))
                .setContentType(CONTENT_TYPE_JSON)
                .build();
    }
}