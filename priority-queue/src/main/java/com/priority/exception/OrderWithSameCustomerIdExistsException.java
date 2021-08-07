package com.priority.exception;

public class OrderWithSameCustomerIdExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public OrderWithSameCustomerIdExistsException(int customerId) {
		super("Order with same Customer ID " + customerId + " exists !");
	}

}
