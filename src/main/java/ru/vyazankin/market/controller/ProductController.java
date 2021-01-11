package ru.vyazankin.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.vyazankin.market.entity.Product;
import ru.vyazankin.market.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public Page<Product> getProducts(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "products", required = false) Integer products){
        if (page == null) page = 0;
        if (products == null) products = 0;
        return productService.getProductPage(PageRequest.of(page, products, Sort.by("title")));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.findProductById(id).get();
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
    }

}
