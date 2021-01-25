package ru.vyazankin.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CartItemDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer count;
}
