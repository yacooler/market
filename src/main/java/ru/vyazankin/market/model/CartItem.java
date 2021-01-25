package ru.vyazankin.market.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;


@Data
@AllArgsConstructor

public class CartItem {

    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal itemsPrice;

    public CartItem incQuantityAndRecalc(){
        quantity++;
        recalc();
        return this;
    }

    public CartItem decQuantityAndRecalc(){
        quantity--;
        recalc();
        return this;
    }

    private CartItem recalc(){
        itemsPrice = price.multiply(BigDecimal.valueOf(quantity));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item = (CartItem) o;
        return productId.equals(item.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
