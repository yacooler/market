package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vyazankin.market.bean.Cart;
import ru.vyazankin.market.dto.CartDto;
import ru.vyazankin.market.service.ProductService;

import java.security.Principal;

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


}
