package com.priority.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.priority.model.OrderStatus;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class StorehouseController {

	@GetMapping("createOrder")
	public ResponseEntity<?> createNewOrder(int customerId, int quantity) {
		log.info("lombok stuff");
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("queuePosition")
	public ResponseEntity<OrderStatus> getQueuePosition(int customerId) {
		OrderStatus os = OrderStatus.builder().build();
		return new ResponseEntity<>(os, HttpStatus.OK);
	}

	@GetMapping("orders")
	public ResponseEntity<List<OrderStatus>> getAllOrders() {
		return null;

	}

	@GetMapping("nextDelivery")
	public ResponseEntity<OrderStatus> getNextDelivery() {
		return null;

	}

	@GetMapping("cancelOrder")
	public ResponseEntity<?> cancelOrder(int customerId) {
		return null;
	}
}
