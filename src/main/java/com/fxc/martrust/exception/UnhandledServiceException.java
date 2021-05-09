package com.fxc.martrust.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnhandledServiceException extends RuntimeException{

	private static final long serialVersionUID = 6981035911034815706L;

	public UnhandledServiceException() {
		super();
	}

	public UnhandledServiceException(String message) {
		super(message);
	}

	public UnhandledServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnhandledServiceException(Throwable cause) {
		super(cause);
	}
}
