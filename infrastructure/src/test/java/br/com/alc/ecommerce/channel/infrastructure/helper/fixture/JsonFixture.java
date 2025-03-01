package br.com.alc.ecommerce.channel.infrastructure.helper.fixture;

import br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.messaging.MessagingDataTable;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.joining;

@Log4j2
public final class JsonFixture {

    private static final String ORDER_REQUEST_DTO_987654321 = ResourceFixture.getContentFromResourceJson("/fixtures/OrderRequestDto-987654321.json");

    private static final Map<MessagingDataTable, String> jsons;
    private static final Map<MessagingDataTable, String> unusedJsons;

    static {
        jsons = new HashMap<>();

        jsons.put(MessagingDataTable.builder().queueName("order-queue").jsonKey("987654321").build(), mergeJsons(ORDER_REQUEST_DTO_987654321));

        unusedJsons = new HashMap<>(jsons);
    }

    private JsonFixture() {
    }

    public static String oneJson(MessagingDataTable messagingDataTable) {
        unusedJsons.remove(messagingDataTable);
        log.info("Unused JSONs: " + unusedJsons.keySet());
        return jsons.get(messagingDataTable);
    }

    private static String mergeJsons(String... jsons) {
        return Arrays.stream(jsons)
                .sorted(naturalOrder())
                .collect(joining("; ", "", ""));
    }
}