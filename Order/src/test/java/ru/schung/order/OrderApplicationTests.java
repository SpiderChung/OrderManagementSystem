package ru.schung.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.schung.order.controller.OrderController;
import ru.schung.order.model.Order;
import ru.schung.order.model.OrderItemDTO;
import ru.schung.order.model.OrderNumber;
import ru.schung.order.repository.OrderRepository;
import ru.schung.order.service.OrderItemService;
import ru.schung.order.service.OrderService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;
	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private OrderItemService orderItemService;
	@MockBean
	private OrderRepository orderRepository;

	@Autowired
	private ObjectMapper objectMapper;

//	@Test
//	public void createOrderTest() throws Exception {
//		List<OrderItemDTO> items = Arrays.asList(new OrderItemDTO(), new OrderItemDTO());
//		Order order = new Order();
//
//		given(orderService.createOrder(anyDouble())).willReturn(order);
//		given(orderItemService.createOrderItems(any(List.class), any(Order.class))).willReturn(items);
//
//		mockMvc.perform(post("/api/order/create")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(objectMapper.writeValueAsString(items)))
//				.andExpect(status().isOk())
//				.andExpect(content().json(objectMapper.writeValueAsString(order)));
//	}

//	@Test
//	void createOrderTest() {
//		when(restTemplate.getForObject(anyString(), eq(OrderNumber.class)))
//				.thenReturn(new OrderNumber(123456789L, "220101"));
//
//		Order order = new Order();
//		order.setOrderNumber(123456789L);
//		// Настройте дополнительные свойства order по необходимости
//
//		when(orderRepository.save(any(Order.class))).thenReturn(order);
//
//		orderService.createOrder(100.0);
//
//
//		verify(orderRepository).save(any(Order.class));
//		// Добавьте дополнительные проверки, чтобы убедиться, что созданный заказ соответствует ожиданиям
//	}

//	@Test
//	void createOrderTest() {
//		// Подготовка
//		Long expectedOrderNumber = 123456789L;
//		String expectedDateStr = "220101";
//		Double expectedQuantity = 100.0;
//
//		OrderNumber mockOrderNumber = new OrderNumber(expectedOrderNumber, expectedDateStr);
//		when(restTemplate.getForObject(anyString(), eq(OrderNumber.class)))
//				.thenReturn(mockOrderNumber);
//
//		Order mockOrder = new Order();
//		mockOrder.setOrderNumber(expectedOrderNumber);
//		mockOrder.setTotalAmount(expectedQuantity);
//		mockOrder.setOrderDate(OrderService.convertStringToDate(expectedDateStr));
//		when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);
//
//		System.out.println(restTemplate.getForObject("sdfs", OrderNumber.class));
//		// Действие
//		Order createdOrder = orderService.createOrder(expectedQuantity);
//
//
//		// Проверка
////		verify(orderRepository).save(any(Order.class));
//		assertThat(createdOrder.getOrderNumber()).isEqualTo(expectedOrderNumber);
//		assertThat(createdOrder.getTotalAmount()).isEqualTo(expectedQuantity);
//		assertThat(createdOrder.getOrderDate()).isEqualTo(OrderService.convertStringToDate(expectedDateStr));
//	}

	@Test
	public void getMaxTotalOrderTest() throws Exception {
		String date = "240401";
		Long expectedOrderNumber = 123456789L;
		Order order = new Order();
		order.setOrderDate(new Date());
		order.setOrderNumber(expectedOrderNumber);

		given(orderService.getMaxTotalOrder(date)).willReturn(order);

		mockMvc.perform(get("/api/order/maxTotal")
						.param("date", date))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedOrderNumber.toString()));
	}

//	@Test
//	void getMaxTotalOrderTest() {
//		Date testDate = new Date();
//		Order testOrder = new Order();
//		testOrder.setOrderNumber(123L);
//		testOrder.setTotalAmount(200.0);
//		testOrder.setOrderDate(testDate);
//
//		when(orderRepository.findTopByOrderDateOrderByTotalAmount(testDate)).thenReturn(testOrder);
//
//		Order resultOrder = orderService.getMaxTotalOrder("220101");
//
//		verify(orderRepository).findTopByOrderDateOrderByTotalAmount(testDate);
//		assertEquals(123L, resultOrder.getOrderNumber());
//	}

	@Test
	public void getOrdersWithoutItemTest() throws Exception {
		Long itemId = 1L;
		String startDate = "240101";
		String endDate = "241231";
		List<Long> expectedOrderNumbers = Arrays.asList(123456789L, 456123456L);

		given(orderService.getOrdersWithoutItem(itemId, startDate, endDate)).willReturn(expectedOrderNumbers);

		mockMvc.perform(get("/api/order/withoutItem")
						.param("itemId", itemId.toString())
						.param("startDate", startDate)
						.param("endDate", endDate))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(expectedOrderNumbers)));
	}

}
