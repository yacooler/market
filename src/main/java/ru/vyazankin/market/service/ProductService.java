package ru.vyazankin.market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vyazankin.market.dto.ProductDto;
import ru.vyazankin.market.exceptions.ResourceNotFoundWSException;
import ru.vyazankin.market.model.Product;
import ru.vyazankin.market.repository.ProductRepository;
import ru.vyazankin.market.ws.products.ProductWS;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Пример реализации функционального интерфейса ProductWS f(Product)
     */
    private static final Function<Product, ProductWS> functionEntityToSoap = input -> {
        ProductWS productWS = new ProductWS();
        productWS.setId(input.getId());
        productWS.setTitle(input.getTitle());
        productWS.setPrice(input.getPrice());
        return productWS;
    };


    public List<ProductDto> findAll(){
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toUnmodifiableList());
    }

    public Optional<ProductDto> findProductById(Long id){
       return productRepository.findById(id).map(ProductDto::new);
    }

    public Optional<Product> findRealProductById(Long id){
        return productRepository.findById(id);
    }

    public ProductDto saveOrUpdate(ProductDto productDTO){
        Product product;
        if (productDTO.getId() != null && productRepository.existsById(productDTO.getId())){
            //update
            product = productDTO.updateEntity(productRepository.findById(productDTO.getId()).get());
        } else {
            //save new
            product = productDTO.toEntity();
        }

        productRepository.save(product);
        productDTO.setId(product.getId());

        return productDTO;
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public Page<ProductDto> getProductPage(Pageable pageable){
        return productRepository.findAll(pageable).map(ProductDto::new);
    }

    public ProductWS getProductWSById(Long id){
        //Свой класс исключений, чтобы в идеале завернуть их в поле fault ответа
        return productRepository.findById(id)
                .map(functionEntityToSoap)
                .orElseThrow(()->new ResourceNotFoundWSException("Не удалось найти продукт по ID"));
    }

    public List<ProductWS> getAllProductsWS(){
        return productRepository.findAll().stream()
                .map(functionEntityToSoap)
                .collect(Collectors.toList());
    }

}
