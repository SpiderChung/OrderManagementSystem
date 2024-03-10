package ru.schung.generateordernumber.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OrderNumberService {

    /**
     * Генерация случайного 9-значного числа
     * @return
     */
    public Long generateRandomOrderNumber() {
        Random random = new Random();
        Long randomNum = random.nextLong(900000000) + 100000000;
        return randomNum;
    }

    /**
     * Получение текущей даты в формате "YYMMDD"
     * @return
     */
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }
}
