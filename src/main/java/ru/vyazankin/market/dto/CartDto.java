package ru.vyazankin.market.dto;

import lombok.Data;
import ru.vyazankin.market.bean.Cart;
import ru.vyazankin.market.model.CartItem;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Set<CartItem> cartItems;
    private BigDecimal totalPrice;

    public CartDto(Cart cart) {
        cartItems = cart.getCartItemSet();
        totalPrice = cart.getTotalCartPrice();
    }
}
