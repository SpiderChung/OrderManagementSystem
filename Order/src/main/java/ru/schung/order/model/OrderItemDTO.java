package ru.schung.order.model;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String itemName;
    private Double quantity;
}
