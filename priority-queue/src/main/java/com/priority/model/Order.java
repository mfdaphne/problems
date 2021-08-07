package com.priority.model;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order implements Comparable<Order> {

	private LocalTime time;

	private int customerId;

	private int quantity;

	@Override
	public int compareTo(Order o) {
		return o.getTime().compareTo(time);
	}

}
