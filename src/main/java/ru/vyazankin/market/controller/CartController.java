package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vyazankin.market.bean.Cart;
import ru.vyazankin.market.dto.CartItemDto;
import ru.vyazankin.market.dto.ProductDto;
import ru.vyazankin.market.entity.Product;
import ru.vyazankin.market.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;
    private final Cart cart;


    @GetMapping()
    public List<CartItemDto> getCart(){
        return cart.getCartItemDtos();
    }

    @PostMapping()
    public void addProductToCart(@RequestParam Long id){
        cart.addProductToCart(productService.findRealProductById(id).get());
    }

    @DeleteMapping("/{id}")
    public void removeProductFromCart(@PathVariable Long id){
        cart.removeProductFromCart(productService.findRealProductById(id).get());
    }
}
