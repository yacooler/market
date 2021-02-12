package ru.vyazankin.market.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.vyazankin.market.exceptions.ResourceNotFoundException;
import ru.vyazankin.market.model.OrderItem;
import ru.vyazankin.market.model.Product;
import ru.vyazankin.market.service.ProductService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;


@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class Cart {

    private final ProductService productService;


    @Getter
    private Set<OrderItem> orderItems;

    @Getter
    private BigDecimal totalCartPrice;

    @Getter
    private Integer totalItems;


    @PostConstruct
    private void init(){
        orderItems = new HashSet<>();
        totalCartPrice = BigDecimal.ZERO;
        totalItems = 0;
    }

    public void addProductToCart(Long productId){

        Product product = productService.findRealProductById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Не удалось найти продукт с id = " + productId));

        OrderItem orderItem = orderItems.stream()
                .filter(oi -> oi.getProduct().equals(product)).findAny()  //Нашли OrderItem с нужным продуктом
                .orElseGet( () -> new OrderItem(product,0,BigDecimal.ZERO)); //Не нашли - добавляем новый

        orderItem.setTotalItems(orderItem.getTotalItems() + 1);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getTotalItems())));

        if (orderItem.getTotalItems().equals(1)) {
            orderItems.add(orderItem);
        }

        //log.info(orderItem.toString());

        totalCartPrice = totalCartPrice.add(product.getPrice());
        totalItems++;

    }

    public void removeProductFromCart(Long productId){

        Product product = productService.findRealProductById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Не удалось найти продукт с id = " + productId));

        Optional<OrderItem> optionalOrderItem = orderItems.stream()
                .filter(oi -> oi.getProduct().equals(product)).findAny();

        if (optionalOrderItem.isEmpty()) return;

        OrderItem orderItem = optionalOrderItem.get();
        orderItem.setTotalItems(orderItem.getTotalItems() - 1);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getTotalItems())));
        totalCartPrice = totalCartPrice.subtract(product.getPrice());

        if (orderItem.getTotalItems().equals(0)){
            orderItems.remove(orderItem);
        }
        totalItems--;
    }

    public void clear(){
        orderItems.clear();
        totalCartPrice = BigDecimal.ZERO;
        totalItems = 0;
    }

}
