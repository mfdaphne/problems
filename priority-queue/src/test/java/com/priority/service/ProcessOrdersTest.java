package com.priority.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.priority.exception.CustomerIdNotExistsException;
import com.priority.exception.OrderWithSameCustomerIdExistsException;
import com.priority.exception.OutOfStockException;
import com.priority.model.Order;

public class ProcessOrdersTest {

	private ProcessOrders processOrders;

	@BeforeEach
	public void init() {
		processOrders = new ProcessOrders();

	}

	@Test
	public void shouldAddNewOrders() throws OrderWithSameCustomerIdExistsException {
		int customerId = 10;
		processOrders.addNewOrder(customerId, 5);

		List<Order> orders = processOrders.getAllOrders();

		assertThat(orders).hasSize(1);
		assertThat(orders.get(0).getCustomerId()).isEqualTo(customerId);

	}

	@Test
	public void shouldThrowOrderWithSameCustomerIdExistsException_whenOrderExistsForCustomer()
			throws OrderWithSameCustomerIdExistsException {
		int customerId = 10;
		processOrders.addNewOrder(customerId, 5);

		assertThrows(OrderWithSameCustomerIdExistsException.class, () -> processOrders.addNewOrder(customerId, 20));
	}

	@Test
	public void shouldReturnQueuePosition_whenCustomerIdIsGiven()
			throws OrderWithSameCustomerIdExistsException, CustomerIdNotExistsException {
		int customerId = 10;
		processOrders.addNewOrder(1, 5);
		processOrders.addNewOrder(customerId, 5);
		processOrders.addNewOrder(4, 5);

		int actual = processOrders.getQueuePosition(customerId);

		assertThat(actual).isEqualTo(2);

	}

	@Test
	public void shouldThrowCustomerIdNotExistsException_whenGivenIdIsNotPresent()
			throws OrderWithSameCustomerIdExistsException, CustomerIdNotExistsException {
		processOrders.addNewOrder(1, 5);

		assertThrows(CustomerIdNotExistsException.class, () -> processOrders.getQueuePosition(10));

	}

	@Test
	public void shouldThrowCustomerIdNotExistsException_whenIdNotFoundForCancellingOrder() {

		assertThrows(CustomerIdNotExistsException.class, () -> processOrders.cancelOrder(10));

	}

	@Test
	public void shouldRemoveOrder_whenPresent()
			throws OrderWithSameCustomerIdExistsException, CustomerIdNotExistsException {
		processOrders.addNewOrder(1, 5);
		processOrders.addNewOrder(2, 10);

		processOrders.cancelOrder(1);

		List<Order> orders = processOrders.getAllOrders();

		assertThat(orders).hasSize(1);
	}

	@Test
	public void shouldGetListOfNextDelivery_withCapacityOf25()
			throws OrderWithSameCustomerIdExistsException, OutOfStockException {
		processOrders.addNewOrder(1, 5);
		processOrders.addNewOrder(2, 10);
		processOrders.addNewOrder(3, 5);
		processOrders.addNewOrder(4, 10);

		List<Order> result = processOrders.getNextDelivery();

		assertThat(result).hasSize(3);
		assertThat(result).anyMatch(a -> a.getCustomerId() != 4);
	}

	@Test
	public void checkConcurrency() {
		ExecutorService service = null;

		try {
			service = Executors.newFixedThreadPool(4);

			service.submit(() -> {
				try {
					tasks();
				} catch (OrderWithSameCustomerIdExistsException | OutOfStockException e) {
					e.printStackTrace();
				}
			});

			service.submit(() -> assertThat(processOrders.getCurrentStock()).isEqualTo(80));

		} finally {
			if (service != null) {
				service.shutdown();
			}
		}

	}

	private void tasks() throws OrderWithSameCustomerIdExistsException, OutOfStockException {
		processOrders.addNewOrder(new Random().nextInt(20000), 5);
		processOrders.getNextDelivery();
	}
}
