package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.finder;

import br.com.alc.ecommerce.channel.infrastructure.helper.fixture.ResourceFixture;
import lombok.*;

import java.io.Serializable;

import static lombok.AccessLevel.NONE;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinderResponseDataTable implements Serializable {

    @Getter(NONE)
    private String json;

    public String getJson() {
        if (json == null || "".equalsIgnoreCase(json)) {
            return null;
        }
        if (json.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResourceJson(json);
        }
        return json;
    }
}