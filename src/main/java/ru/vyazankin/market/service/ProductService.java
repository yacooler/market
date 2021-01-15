package ru.vyazankin.market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vyazankin.market.entity.Product;
import ru.vyazankin.market.repository.ProductRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return Collections.unmodifiableList(productRepository.findAll());
    }

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public Product saveOrUpdate(Product product){
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public Page<Product> getProductPage(Pageable pageable){
        return productRepository.findAll(pageable);
    }

}
