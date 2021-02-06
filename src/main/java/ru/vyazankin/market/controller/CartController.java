package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vyazankin.market.bean.Cart;
import ru.vyazankin.market.dto.CartDto;
import ru.vyazankin.market.model.CartItem;
import ru.vyazankin.market.service.ProductService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final Cart cart;

    @GetMapping()
    public CartDto getCart(){
        return new CartDto(cart);
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id){
        cart.addProductToCart(id);
    }

    @GetMapping("/remove/{id}")
    public void removeProductFromCart(@PathVariable Long id){
        cart.removeProductFromCart(id);
    }

    @GetMapping("/clear")
    public void clearCart(){
        cart.clear();
    }

    @GetMapping("/makeorder")
    public void makeOrder(Principal principal){
        //т.к. персональных скидок у нас нет, Principal нам явно пригождается первый раз при заказе
        System.out.println("Попытка сделать заказ от имени: " + principal.getName());
    }

}
