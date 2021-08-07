package com.priority.exception;

public class CustomerIdNotExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CustomerIdNotExistsException(int customerId) {
		super("Customer ID " + customerId + " does not exists !");
	}
}
