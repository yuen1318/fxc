package com.fxc.martrust.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidReqBodyException extends HandledServiceException{

	private static final long serialVersionUID = 8648138277251372517L;

	public InvalidReqBodyException() {
		super();
	}

	public InvalidReqBodyException(String message) {
		super(message);
	}

	public InvalidReqBodyException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidReqBodyException(Throwable cause) {
		super(cause);
	}
}
