package br.com.alc.ecommerce.channel.core.service.generator.impl;

import br.com.alc.ecommerce.channel.core.domain.generator.OrderGeneratorRequest;
import br.com.alc.ecommerce.channel.core.domain.address.AddressResponse;
import br.com.alc.ecommerce.channel.core.domain.order.*;
import br.com.alc.ecommerce.channel.core.service.generator.OrderGeneratorService;
import br.com.alc.ecommerce.channel.core.service.watch.WatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static br.com.alc.ecommerce.channel.core.domain.order.DocumentType.CPF;
import static br.com.alc.ecommerce.channel.core.domain.order.PaymentMethod.*;
import static br.com.alc.ecommerce.channel.core.util.ObjectMapperUtil.generateJson;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static java.util.Collections.emptyList;

@Log4j2
@AllArgsConstructor
public final class OrderGeneratorServiceImpl implements OrderGeneratorService {

    private final WatchService watchService;

    @Override
    public OrderRequest execute(OrderGeneratorRequest orderGeneratorRequest, AddressResponse addressResponse) {
        log.info("Incoming into OrderGeneratorServiceImpl: {} - {}", generateJson(orderGeneratorRequest), generateJson(addressResponse));
        List<ShoppingCartItem> items = buildRandomItems();
        BigDecimal totalValue = buildTotalValue(items);
        BigDecimal freightValue = buildRandomFreightValue(totalValue);
        OrderRequest orderRequest = OrderRequest.builder()
                .channelCode(buildRandomChannelCode())
                .companyCode(buildRandomCompanyCode())
                .storeCode(buildRandomStoreCode())
                .pos(buildRandomPos())
                .totalValue(totalValue)
                .freightValue(freightValue)
                .orderNumber(orderGeneratorRequest.getOrderNumber())
                .customer(buildRandomCustomer(addressResponse))
                .items(items)
                .payments(buildRandomPayments(totalValue, freightValue))
                .build();
        log.info("Outgoing from OrderGeneratorServiceImpl: {}", generateJson(orderRequest));
        return orderRequest;
    }

    private List<ShoppingCartItem> buildRandomItems() {
        return new Faker().collection(this::buildRandomItem).len(1, 10).generate();
    }

    private ShoppingCartItem buildRandomItem() {
        return ShoppingCartItem.builder()
                .code(buildRandomCode())
                .quantity(buildRandomQuantity())
                .value(buildRandomItemValue())
                .build();
    }

    private BigInteger buildRandomCode() {
        long randomCode = ThreadLocalRandom.current().nextLong(999999999);
        return BigInteger.valueOf(randomCode);
    }

    private Integer buildRandomQuantity() {
        return ThreadLocalRandom.current().nextInt(1, 10);
    }

    private BigDecimal buildRandomItemValue() {
        double randomValue = ThreadLocalRandom.current().nextDouble(1, 100);
        return BigDecimal.valueOf(randomValue).setScale(2, HALF_EVEN);
    }

    private BigDecimal buildTotalValue(List<ShoppingCartItem> items) {
        return Optional.ofNullable(items)
                .orElse(emptyList())
                .stream()
                .map(ShoppingCartItem::getTotalItemValue)
                .reduce(ZERO, BigDecimal::add);
    }

    private BigDecimal buildRandomFreightValue(BigDecimal totalValue) {
        return totalValue.multiply(BigDecimal.valueOf(0.01)).setScale(2, HALF_EVEN);
    }

    private String buildRandomChannelCode() {
        List<String> channels = Arrays.asList("WEB", "APP", "STR", "SLF");
        int randomIndex = ThreadLocalRandom.current().nextInt(channels.size());
        return channels.get(randomIndex);
    }

    private String buildRandomCompanyCode() {
        int randomCompanyCode = ThreadLocalRandom.current().nextInt(999);
        return StringUtils.leftPad(String.valueOf(randomCompanyCode), 3, '0');
    }

    private String buildRandomStoreCode() {
        int randomStoreCode = ThreadLocalRandom.current().nextInt(999);
        return StringUtils.leftPad(String.valueOf(randomStoreCode), 3, '0');
    }

    private Integer buildRandomPos() {
        return ThreadLocalRandom.current().nextInt(999);
    }

    private Customer buildRandomCustomer(AddressResponse addressResponse) {
        DocumentType documentType = buildRandomDocumentType();
        String name = buildRandomName(documentType);
        return Customer.builder()
                .name(name)
                .document(buildRandomDocument(documentType))
                .documentType(documentType)
                .address(addressResponse.getCity())
                .addressNumber(buildRandomAddressNumber())
                .addressComplement(addressResponse.getComplement())
                .neighborhood(addressResponse.getNeighborhood())
                .city(addressResponse.getCity())
                .state(addressResponse.getState())
                .country("Brasil")
                .zipCode(addressResponse.getZipCode())
                .phone(buildRandomPhone(addressResponse.getDdd()))
                .email(buildRandomEmail(name))
                .build();
    }

    private DocumentType buildRandomDocumentType() {
        List<DocumentType> documents = Arrays.stream(DocumentType.values()).toList();
        int randomIndex = ThreadLocalRandom.current().nextInt(documents.size());
        return documents.get(randomIndex);
    }

    private String buildRandomName(DocumentType documentType) {
        return Optional.ofNullable(documentType)
                .filter(CPF::equals)
                .map(d -> new Faker().name().fullName())
                .orElse(new Faker().company().name());
    }

    private String buildRandomDocument(DocumentType documentType) {
        return Optional.ofNullable(documentType)
                .filter(CPF::equals)
                .map(d -> new Faker().cpf().valid())
                .orElse(new Faker().cnpj().valid());
    }

    private String buildRandomAddressNumber() {
        int addressNumber = ThreadLocalRandom.current().nextInt(999);
        return String.valueOf(addressNumber);
    }

    private String buildRandomPhone(String ddd) {
        int randomNumber = 10000000 + ThreadLocalRandom.current().nextInt(90000000);
        return String.format("(%d) %d%d-%04d", Integer.valueOf(ddd), 9, randomNumber / 10000, randomNumber % 10000);
    }

    private String buildRandomEmail(String name) {
        String[] nameParts = name.trim().toLowerCase().split("\\s+");
        return nameParts[0] + (nameParts.length > 1 ? "." + nameParts[nameParts.length - 1] : "") + "@gmail.com";
    }

    private List<Payment> buildRandomPayments(BigDecimal totalValue, BigDecimal freightValue) {
        PaymentMethod paymentMethod = buildRandomPaymentMethod();
        Payment payment = Payment.builder()
                .paymentMethod(paymentMethod)
                .paymentDate(watchService.nowLocalDateTime())
                .authorizationCode(buildRandomAuthorizationCode())
                .cardNumber(buildRandomCardNumber(paymentMethod))
                .pixKey(buildRandomPixKey(paymentMethod))
                .value(totalValue.add(freightValue))
                .build();
        return Arrays.asList(payment);
    }

    private PaymentMethod buildRandomPaymentMethod() {
        List<PaymentMethod> methods = Arrays.stream(PaymentMethod.values()).toList();
        int randomIndex = ThreadLocalRandom.current().nextInt(methods.size());
        return methods.get(randomIndex);
    }

    private String buildRandomAuthorizationCode() {
        int randomAuthorizationCode = ThreadLocalRandom.current().nextInt(999999);
        return String.valueOf(randomAuthorizationCode);
    }

    private String buildRandomCardNumber(PaymentMethod paymentMethod) {
        return Optional.ofNullable(paymentMethod)
                .filter(p -> CREDIT.equals(p) || DEBIT.equals(p))
                .map(p -> {
                    int randomPrefix = ThreadLocalRandom.current().nextInt(10000000, 99999999);
                    int randomSufix = ThreadLocalRandom.current().nextInt(10000000, 99999999);
                    return "" + randomPrefix + randomSufix;
                })
                .orElse(null);
    }

    private String buildRandomPixKey(PaymentMethod paymentMethod) {
        return Optional.ofNullable(paymentMethod)
                .filter(PIX::equals)
                .map(p -> UUID.randomUUID().toString())
                .orElse(null);
    }
}