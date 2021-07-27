package com.priority.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatus {

	private int customerId;

	private int quantity;

	private int queuePosition;

	private float aproxWaitingTime;

}
