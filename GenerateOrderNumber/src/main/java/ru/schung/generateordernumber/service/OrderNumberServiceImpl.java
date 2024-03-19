package ru.schung.generateordernumber.service;

import org.springframework.stereotype.Service;
import ru.schung.generateordernumber.model.OrderNumber;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OrderNumberServiceImpl implements OrderNumberService {

    /**
     * Генерация случайного 9-значного числа
     * @return 9-значное число
     */
    @Override
    public Long generateRandomOrderNumber() {
        Random random = new Random();
        Long randomNum = random.nextLong(900000000) + 100000000;
        return randomNum;
    }

    /**
     * Получение текущей даты в формате "YYMMDD"
     * @return Текущая дата
     */
    @Override
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }


    /**
     * @return структура номера заказа: номер и дата
     */
    @Override
    public OrderNumber createOrderNumber() {
        return new OrderNumber(generateRandomOrderNumber(), getCurrentDateTime());
    }


}
