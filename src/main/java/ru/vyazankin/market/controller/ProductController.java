package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.vyazankin.market.bean.Cart;
import ru.vyazankin.market.dto.ProductDto;
import ru.vyazankin.market.entity.Product;
import ru.vyazankin.market.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;



    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "products", required = false) Integer products){
        if (page == null || page < 1) page = 1;
        if (products == null) products = 0;
        return productService.getProductPage(PageRequest.of(page - 1, products, Sort.by("title")));
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return productService.findProductById(id).get();
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDTO){
        return productService.saveOrUpdate(productDTO);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDTO){
        return productService.saveOrUpdate(productDTO);
    }



}
