package ru.vyazankin.market.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.vyazankin.market.bean.Cart;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class CartDto {
    private List<CartItemDto> cartItemDtos;
    private BigDecimal totalPrice;

    public CartDto(Cart cart) {
        cartItemDtos = cart.getOrderItems().stream().map(CartItemDto::new)
                .sorted(Comparator.comparing(CartItemDto::getTitle))
                .collect(Collectors.toList());

        totalPrice = cart.getTotalCartPrice();


    }
}
