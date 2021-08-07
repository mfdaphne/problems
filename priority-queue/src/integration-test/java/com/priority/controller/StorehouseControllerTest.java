package com.priority.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.priority.exception.CustomerIdNotExistsException;
import com.priority.exception.OrderWithSameCustomerIdExistsException;
import com.priority.service.ProcessOrders;

@SpringBootTest
@AutoConfigureMockMvc
public class StorehouseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProcessOrders processOrders;

	private static final String BASE_URL = "/storehouse";

	@Test
	public void shouldCreateNewOrder_whenCustomerIdAndQuantityArePositive() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/createOrder").queryParam("customerId", "100")
				.queryParam("quantity", "10");

		mockMvc.perform(request).andExpect(status().isOk());

	}

	@Test
	public void shouldThrowOrderWithSameCustomerIdExistsException_whenCustomerAlreadyOrdered() throws Exception {
		processOrders.addNewOrder(200, 20);

		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/createOrder").queryParam("customerId", "200")
				.queryParam("quantity", "10");

		mockMvc.perform(request).andExpect(status().isBadRequest())
				.andExpect(result -> Assertions.assertThat(result.getResolvedException().getClass())
						.isEqualTo(OrderWithSameCustomerIdExistsException.class));
	}

	@Test
	public void shouldGetQueuePosition_whenCustomerIdPresent() throws Exception {
		processOrders.addNewOrder(101, 20);

		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/queuePosition").queryParam("customerId",
				"101");

		mockMvc.perform(request).andExpect(status().isOk()).andExpect(result -> {
			assertThat(Integer.parseInt(result.getResponse().getContentAsString())).isGreaterThanOrEqualTo(1);
		});

	}

	@Test
	public void shouldThrowCustomerIdNotExistsException_whenCustomerIdNotPresent() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/queuePosition").queryParam("customerId",
				"1000");

		mockMvc.perform(request).andExpect(status().isNotFound()).andExpect(result -> Assertions
				.assertThat(result.getResolvedException().getClass()).isEqualTo(CustomerIdNotExistsException.class));

	}

	@Test
	public void shouldReturnOkStatus_whenOrdersArePresent() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/orders");

		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void shouldReturnOkStatus_whenNextDeliveryIsPresent() throws Exception {

		processOrders.addNewOrder(201, 20);

		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/nextDelivery");

		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void shouldReturnOkStatus_whenOrderCancelled() throws Exception {
		processOrders.addNewOrder(105, 20);
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/cancelOrder").queryParam("customerId", "105");

		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void shouldThrowCustomerIdNotExistsException_whenCustomerIdForOrderCancellationNotPresent()
			throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/cancelOrder").queryParam("customerId", "200");

		mockMvc.perform(request).andExpect(status().isNotFound()).andExpect(result -> Assertions
				.assertThat(result.getResolvedException().getClass()).isEqualTo(CustomerIdNotExistsException.class));

	}

}
