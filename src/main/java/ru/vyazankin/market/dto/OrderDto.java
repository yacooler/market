package ru.vyazankin.market.dto;


import lombok.Data;
import ru.vyazankin.market.model.Order;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private Long id;
    private Integer totalItems;
    private BigDecimal totalPrice;
    private String deliveryAddress;

    public OrderDto(Order order){
        this.id = order.getId();
        this.totalItems = order.getTotalItems();
        this.totalPrice = order.getTotalPrice();
        this.deliveryAddress = order.getDeliveryAddress();
    }

}
