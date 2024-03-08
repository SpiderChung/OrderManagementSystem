package ru.schung.generateordernumber.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderNumber {
    private Long orderNumber;
    private String dateTime;

}