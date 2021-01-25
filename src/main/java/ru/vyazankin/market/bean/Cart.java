package ru.vyazankin.market.bean;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vyazankin.market.dto.CartItemDto;
import ru.vyazankin.market.dto.ProductDto;
import ru.vyazankin.market.entity.Product;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@NoArgsConstructor
public class Cart {
    private Map<Product, Integer> products;

    @PostConstruct
    private void init(){
        products = new HashMap<>();
    }

    public void addProductToCart(Product product){
        products.compute(product, (prod, count) -> count == null ? 1 : ++count);
    }

    public void removeProductFromCart(Product product){
        //если результат лямбды null - автоматически выполнится remove(product)
        products.computeIfPresent(product, (prod, count) -> count == 1 ? null : --count);
    }

    public List<CartItemDto> getCartItemDtos(){
        return products.entrySet().stream()
                .map(entry -> new CartItemDto(
                        entry.getKey().getId(),
                        entry.getKey().getTitle(),
                        entry.getKey().getPrice(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }
}
