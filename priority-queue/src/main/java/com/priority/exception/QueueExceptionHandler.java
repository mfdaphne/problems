package com.priority.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.priority.exception.base.CustomErrorCode;
import com.priority.exception.base.ErrorResponse;

@RestControllerAdvice
public class QueueExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(OrderWithSameCustomerIdExistsException.class)
	public ResponseEntity<ErrorResponse> orderWithSameCustomerIdExistsException(
			OrderWithSameCustomerIdExistsException ex, WebRequest request) throws IOException {

		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage())
				.timeStamp(LocalDateTime.now()).httpStatusCode(HttpStatus.BAD_REQUEST)
				.errorCode(CustomErrorCode.ORDER_WITH_SAME_ID_EXISTS_EXCEPTION).build();

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomerIdNotExistsException.class)
	public ResponseEntity<ErrorResponse> customerIdNotExistsException(CustomerIdNotExistsException ex,
			WebRequest request) throws IOException {

		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage())
				.timeStamp(LocalDateTime.now()).httpStatusCode(HttpStatus.NOT_FOUND)
				.errorCode(CustomErrorCode.CUSTOMER_ID_NOT_EXISTS_EXCEPTION).build();

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<ErrorResponse> outOfStockException(OutOfStockException ex, WebRequest request)
			throws IOException {

		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage())
				.timeStamp(LocalDateTime.now()).httpStatusCode(HttpStatus.NO_CONTENT)
				.errorCode(CustomErrorCode.OUT_OF_STOCK_EXCEPTION).build();

		return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
	}
}
