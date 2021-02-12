package ru.vyazankin.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.vyazankin.market.bean.Cart;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    public Order(Cart cart){
        totalItems = cart.getTotalItems();
        totalPrice = cart.getTotalCartPrice();
        orderItems = new ArrayList<>(cart.getOrderItems().size());
        cart.getOrderItems().forEach(
                (orderItem) -> {
                    this.orderItems.add(orderItem);
                    orderItem.setOrder(this);
                });
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_items")
    private Integer totalItems;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> orderItems;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

}
