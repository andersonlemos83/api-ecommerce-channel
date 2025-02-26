package br.com.alc.ecommerce.channel.infrastructure.adapter.output.stub;

import br.com.alc.ecommerce.channel.core.domain.order.Order;
import br.com.alc.ecommerce.channel.core.port.output.MostRecentOrderFinderOutPort;
import br.com.alc.ecommerce.channel.infrastructure.adapter.output.MostRecentOrderFinderOutPortImpl;
import br.com.alc.ecommerce.channel.infrastructure.persistence.repository.OrderRepository;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class MostRecentOrderFinderOutPortStub extends MostRecentOrderFinderOutPortImpl implements MostRecentOrderFinderOutPort {

    public static final String INVALID_ORDER_NUMBER = "987654326";

    public MostRecentOrderFinderOutPortStub(OrderRepository orderRepository, ModelMapper modelMapper) {
        super(orderRepository, modelMapper);
    }

    @Override
    public Optional<Order> execute(String orderNumber) {
        if (INVALID_ORDER_NUMBER.equals(orderNumber)) {
            throw new RuntimeException("Erro inesperado ao consultar Order.");
        }
        return super.execute(orderNumber);
    }
}