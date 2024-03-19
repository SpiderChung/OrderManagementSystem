package ru.schung.generateordernumber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.schung.generateordernumber.controller.OrderNumberController;
import ru.schung.generateordernumber.service.OrderNumberServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderNumberController.class)
public class OrderNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderNumberServiceImpl orderNumberService;

    @Test
    public void generateNumberTest() throws Exception {
        Long expectedOrderNumber = 123456789L;
        String expectedDateTime = "220103";

        given(orderNumberService.generateRandomOrderNumber()).willReturn(expectedOrderNumber);
        given(orderNumberService.getCurrentDateTime()).willReturn(expectedDateTime);

        mockMvc.perform(get("/api/order/generate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"orderNumber\":" + expectedOrderNumber + ",\"dateTime\":\"" + expectedDateTime + "\"}"));
    }
}
