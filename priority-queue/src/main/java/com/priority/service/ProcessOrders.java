package com.priority.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.priority.exception.CustomerIdNotExistsException;
import com.priority.exception.OrderWithSameCustomerIdExistsException;
import com.priority.exception.OutOfStockException;
import com.priority.model.Order;

@Service
public class ProcessOrders {

	private AtomicInteger stock = new AtomicInteger(100);
	private static final int CAPACITY = 25;

	private ConcurrentLinkedQueue<Order> orders = new ConcurrentLinkedQueue<>();

	public void addNewOrder(int customerId, int quantity) throws OrderWithSameCustomerIdExistsException {

		if (!orders.stream().anyMatch(o -> o.getCustomerId() == customerId)) {
			Order order = Order.builder().customerId(customerId).quantity(quantity).time(LocalTime.now()).build();
			orders.offer(order);
		} else {
			throw new OrderWithSameCustomerIdExistsException(customerId);
		}

	}

	public int getQueuePosition(int customerId) throws CustomerIdNotExistsException {

		int position = 1;

		for (Order order : orders) {
			if (order.getCustomerId() == customerId) {
				return position;
			}
			position++;
		}

		throw new CustomerIdNotExistsException(customerId);
	}

	public List<Order> getAllOrders() {
		return orders.stream().collect(Collectors.toList());
	}

	public void cancelOrder(int customerId) throws CustomerIdNotExistsException {
		Optional<Order> order = orders.stream().filter(o -> o.getCustomerId() == customerId).findFirst();

		if (!order.isPresent()) {
			throw new CustomerIdNotExistsException(customerId);
		}

		order.ifPresent(o -> orders.remove(o));

	}

	public List<Order> getNextDelivery() throws OutOfStockException {

		int load = 0;
		List<Order> ordersToDeliver = new ArrayList<Order>();

		while (orders.iterator().hasNext()) {
			if (stock.get() < orders.peek().getQuantity()) {
				throw new OutOfStockException();
			}
			if (orders.peek().getQuantity() + load <= CAPACITY) {
				Order polledOrder = orders.poll();
				load = load + polledOrder.getQuantity();

				ordersToDeliver.add(polledOrder);
			} else {
				break;
			}
		}

		ordersToDeliver.stream().forEach(o -> stock.set(stock.intValue() - o.getQuantity()));
		return ordersToDeliver;

	}

	public int getCurrentStock() {
		return stock.get();
	}
}
