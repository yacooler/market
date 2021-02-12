package ru.vyazankin.market.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vyazankin.market.model.Order;
import ru.vyazankin.market.model.User;

import java.util.List;


public interface OrderRepository extends CrudRepository<Order, Long> {
    public List<Order> findOrdersByOwner(User user);
}
