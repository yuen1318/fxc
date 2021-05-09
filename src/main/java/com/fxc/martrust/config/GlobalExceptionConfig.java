package com.fxc.martrust.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fxc.martrust.exception.InvalidReqBodyException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public final class GlobalExceptionConfig extends ResponseEntityExceptionHandler {

	private static final String ERROR_MESSAGE = "Opps something went wrong, please contact the support team";
	private static final String MESSAGE_KEY = "message";

	@ExceptionHandler(value = InvalidReqBodyException.class)
	public ResponseEntity<Object> handleUnprocessableEntity(Exception ex) {
		Map<String, Object> errorResp = new HashMap<>();
		JSONParser parser = new JSONParser();
		JSONArray errArr;

		try {
			errArr = Objects.nonNull(ex.getMessage()) ? (JSONArray) parser.parse(ex.getMessage()) : new JSONArray();
			errorResp.put(MESSAGE_KEY, errArr);
			return ResponseEntity.status(400).body(errorResp);
		} catch (ParseException e) {
			errorResp.put(MESSAGE_KEY, ex.getMessage());
			return ResponseEntity.status(400).body(errorResp);
		}
		
	}
	
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {
		Map<String, Object> errorResp = new HashMap<>();
		errorResp.put(MESSAGE_KEY, ERROR_MESSAGE);
		return ResponseEntity.status(500).body(errorResp);
	}
}
