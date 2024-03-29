package ru.schung.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.schung.order.controller.OrderController;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.service.OrderItemServiceImpl;
import ru.schung.order.service.OrderService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderItemServiceImpl orderItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderTest() throws Exception {
        List<OrderItemDTO> itemsDTO = List.of(new OrderItemDTO("Item1", 1.0));
        Double quantity = 1.0;
        Order order = new Order();
        order.setOrderNumber(123456789L);

        given(orderService.createOrder(anyDouble())).willReturn(order);

        mockMvc.perform(post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(order.getOrderNumber()));
    }

    @Test
    void getMaxTotalOrderTest() throws Exception {
        String date = "240310";
        Long orderNumber = 123456789L;

        given(orderService.getMaxTotalOrder(date)).willReturn(new Order());

        mockMvc.perform(get("/api/order/maxTotal")
                        .param("date", date))
                .andExpect(status().isOk());
    }

    @Test
    void getOrdersWithoutItemTest() throws Exception {
        String itemName = "sock";
        String startDate = "240301";
        String endDate = "240310";
        List<Long> orderNumbers = List.of(123456789L);

        given(orderService.getOrdersWithoutItem(itemName, startDate, endDate)).willReturn(orderNumbers);

        mockMvc.perform(get("/api/order/withoutItem")
                        .param("itemName", itemName)
                        .param("startDate", startDate)
                        .param("endDate", endDate))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderNumbers)));
    }
}
