package ru.vyazankin.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vyazankin.market.entity.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private BigDecimal price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
    }

    public Product toEntity(){
        return updateEntity(new Product());
    }

    public Product updateEntity(Product product){
        product.setTitle(this.title);
        product.setPrice(this.price);
        return product;
    }
}
