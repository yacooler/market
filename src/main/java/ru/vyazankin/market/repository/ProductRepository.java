package ru.vyazankin.market.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vyazankin.market.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Page<Product> findAll(Pageable pageable);


}
