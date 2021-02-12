package ru.vyazankin.market.dto;

import lombok.*;
import ru.vyazankin.market.model.OrderItem;

import java.math.BigDecimal;

@Data
public class CartItemDto{

    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal itemsPrice;

    public CartItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.title = orderItem.getProduct().getTitle();
        this.price = orderItem.getTotalPrice();
        this.quantity = orderItem.getTotalItems();
        this.itemsPrice = orderItem.getTotalPrice();
    }
}
