package ru.schung.generateordernumber.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OrderNumberService {
    public Long generateRandomOrderNumber() {
        // Генерация случайного 9-значного числа
        Random random = new Random();
        Long randomNum = random.nextLong(900000000) + 100000000;
        return randomNum;
    }

    public String getCurrentDateTime() {
        // Получение текущей даты в формате "YYMMDD"
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }
}
