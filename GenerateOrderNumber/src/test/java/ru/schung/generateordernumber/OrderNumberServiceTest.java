package ru.schung.generateordernumber;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.schung.generateordernumber.service.OrderNumberService;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderNumberServiceTest {

    @Autowired
    private OrderNumberService orderNumberService;

    /**
     * Проверка генерируемого значения на количество знаков
     */
    @Test
    public void generateRandomOrderNumberTest() {
        Long orderNumber = orderNumberService.generateRandomOrderNumber();
        assertTrue(orderNumber >= 100000000 && orderNumber <= 999999999, "Order number should be within the valid range");
    }

    /**
     * Проверка формата даты - YYMMDD
     */
    @Test
    public void getCurrentDateTimeTest() {
        String dateTime = orderNumberService.getCurrentDateTime();
        assertTrue(dateTime.matches("\\d{6}"), "Date time should be in the format YYMMDD");
    }
}