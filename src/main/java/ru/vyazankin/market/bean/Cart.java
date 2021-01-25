package ru.vyazankin.market.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vyazankin.market.exceptions.ResourceNotFoundException;
import ru.vyazankin.market.model.CartItem;
import ru.vyazankin.market.model.Product;
import ru.vyazankin.market.service.ProductService;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class Cart {

    private final ProductService productService;

    @Getter
    private Set<CartItem> cartItemSet;
    @Getter
    private BigDecimal totalCartPrice;

    @PostConstruct
    private void init(){
        cartItemSet = new HashSet<>();
        totalCartPrice = BigDecimal.ZERO;
    }

    public void addProductToCart(Long productId){
        //Получаем продукт из корзины.
        CartItem item = cartItemSet.stream().filter(ci -> ci.getProductId().equals(productId)).findAny()
                    .orElseGet(() -> {
                        //если продукта нет - формируем новый
                        Product product = productService.findRealProductById(productId).orElseThrow( ()-> new ResourceNotFoundException("Не удалось найти продукт с id = " + productId));
                        CartItem cartItem = new CartItem(product.getId(), product.getTitle(), product.getPrice(), 0, BigDecimal.ZERO);
                        cartItemSet.add(cartItem);
                        return cartItem;
                    }).incQuantityAndRecalc();

        totalCartPrice = totalCartPrice.add(item.getPrice());
    }

    public void removeProductFromCart(Long productId){
        Optional<CartItem> cartItem = cartItemSet.stream().filter(ci -> ci.getProductId().equals(productId)).findAny();
        if (cartItem.isEmpty()) return;
        CartItem item = cartItem.get();

        item.decQuantityAndRecalc();
        totalCartPrice = totalCartPrice.subtract(item.getPrice());

        if (item.getQuantity() == 0) {
            cartItemSet.remove(item);
        };
    }

    public void clear(){
        cartItemSet.clear();
        totalCartPrice = BigDecimal.ZERO;
    }

}
