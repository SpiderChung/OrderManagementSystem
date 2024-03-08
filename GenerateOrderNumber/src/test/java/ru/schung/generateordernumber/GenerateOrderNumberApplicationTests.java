package ru.schung.generateordernumber;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.schung.generateordernumber.controller.OrderNumberController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GenerateOrderNumberApplicationTests {
    @Autowired
    private OrderNumberController orderNumberController;


    @Test
    void contextLoads() {
    }

    @Test
    public void testOrderNumber() {
        var responce = orderNumberController.generateNumber();
        assertNotNull(responce.getDateTime());
        assertNotNull(responce.getOrderNumber());
    }


}
