package ru.vyazankin.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.vyazankin.market.dto.CartItemDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
public class OrderItem {

    public OrderItem(Product product, Integer totalItems, BigDecimal totalPrice) {
        this.product = product;
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @Column(name = "products_count")
    private Integer totalItems;

    @Column(name = "products_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Transient
    private Integer orderInList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem item = (OrderItem) o;
        return product.equals(item.product)
                && (this.order == null && item.order == null || order.equals(item.order));
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
