package ru.schung.generateordernumber.service;

import ru.schung.generateordernumber.model.OrderNumber;

public interface OrderNumberService {
    public Long generateRandomOrderNumber();
    public String getCurrentDateTime();
    public OrderNumber createOrderNumber();

}
