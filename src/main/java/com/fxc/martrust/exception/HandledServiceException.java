package com.fxc.martrust.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HandledServiceException extends RuntimeException{

	private static final long serialVersionUID = 5041218992932141753L;

	public HandledServiceException() {
		super();
	}

	public HandledServiceException(String message) {
		super(message);
	}

	public HandledServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public HandledServiceException(Throwable cause) {
		super(cause);
	}
}
