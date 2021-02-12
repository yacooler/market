package ru.vyazankin.market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vyazankin.market.bean.Cart;
import ru.vyazankin.market.dto.OrderDto;
import ru.vyazankin.market.model.Order;
import ru.vyazankin.market.model.User;
import ru.vyazankin.market.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final Cart cart;

    public Order saveOrderFromUserCart(User user, String deliveryAddress){
        Order order = new Order(this.cart);
        order.setOwner(user);
        order.setDeliveryAddress(deliveryAddress);
        return orderRepository.save(order);
    }

    public List<OrderDto> orderParamDtoList(User user){
        return orderRepository.findOrdersByOwner(user).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
