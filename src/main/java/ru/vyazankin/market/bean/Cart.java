package ru.vyazankin.market.bean;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vyazankin.market.entity.Product;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


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

    public Map<Product, Integer> getProducts(){
        return products;
    }
}
