package com.priority.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.priority.exception.CustomerIdNotExistsException;
import com.priority.exception.OrderWithSameCustomerIdExistsException;
import com.priority.exception.OutOfStockException;
import com.priority.model.Order;
import com.priority.service.ProcessOrders;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/storehouse")
public class StorehouseController {

	private ProcessOrders processOrders;

	public StorehouseController(ProcessOrders processOrders) {
		this.processOrders = processOrders;
	}

	@GetMapping("/createOrder")
	public ResponseEntity<?> createNewOrder(@RequestParam(name = "customerId") @Min(1) @Max(20000) int customerId,
			@RequestParam(name = "quantity") @Min(1) int quantity) throws OrderWithSameCustomerIdExistsException {
		log.info("Order creation initiated");
		processOrders.addNewOrder(customerId, quantity);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/queuePosition")
	public ResponseEntity<Integer> getQueuePosition(@RequestParam(name = "customerId") int customerId)
			throws CustomerIdNotExistsException {

		int queuePosition = processOrders.getQueuePosition(customerId);
		return ResponseEntity.ok(queuePosition);
	}

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> allOrders = processOrders.getAllOrders();
		return !allOrders.isEmpty() ? ResponseEntity.ok(allOrders) : ResponseEntity.noContent().build();

	}

	@GetMapping("/nextDelivery")
	public ResponseEntity<List<Order>> getNextDelivery() throws OutOfStockException {
		List<Order> nextDeliveries = processOrders.getNextDelivery();
		return !nextDeliveries.isEmpty() ? ResponseEntity.ok(nextDeliveries) : ResponseEntity.noContent().build();

	}

	@GetMapping("/cancelOrder")
	public ResponseEntity<?> cancelOrder(@RequestParam(name = "customerId") int customerId)
			throws CustomerIdNotExistsException {
		processOrders.cancelOrder(customerId);
		return ResponseEntity.ok().build();
	}
}
