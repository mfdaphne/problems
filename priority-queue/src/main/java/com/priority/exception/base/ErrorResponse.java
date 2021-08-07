package com.priority.exception.base;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

	private HttpStatus httpStatusCode;

	private String errorMessage;

	private CustomErrorCode errorCode;

	private LocalDateTime timeStamp;

}
