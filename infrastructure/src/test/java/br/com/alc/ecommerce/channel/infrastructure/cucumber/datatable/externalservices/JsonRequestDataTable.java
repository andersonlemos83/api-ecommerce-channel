package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.externalservices;

import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

import static lombok.AccessLevel.NONE;
import static org.springframework.http.HttpStatus.OK;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonRequestDataTable implements Serializable {

    private HttpStatus status;

    @Getter(NONE)
    private String request;

    public String getRequest() {
        if (request == null || "".equalsIgnoreCase(request)) {
            return null;
        }
        if (request.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResourceJson(request);
        }
        return request;
    }

    public boolean isStatusOk() {
        return OK.equals(status);
    }
}