package com.fxc.martrust.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionConfigTest {

	private static final String ERROR_MESSAGE = "Opps something went wrong, please contact the support team";
	private static final String MESSAGE_KEY = "message";

	@InjectMocks
	private GlobalExceptionConfig globalExceptionConfig;
	
	
	@DisplayName("test handleUnprocessableEntity")
	@Test
	public void testHandleUnprocessableEntityWith() throws ParseException {
		JSONArray mockArray = new JSONArray();
		mockArray.add("test1");
		mockArray.add("test2");
		mockArray.add("test3");

		Exception ex = new Exception(mockArray.toJSONString());
		
		JSONParser parser = new JSONParser();
		JSONArray errArr = (JSONArray) parser.parse(ex.getMessage());
		
		Map<String, Object> errorResp = new HashMap<>();
		errorResp.put(MESSAGE_KEY, errArr);
		
		ResponseEntity<Object> expected = ResponseEntity.status(400).body(errorResp);
		
		ResponseEntity<Object> actual = this.globalExceptionConfig.handleUnprocessableEntity(ex);
		
		assertEquals(expected, actual);
	}
	
	@DisplayName("test handleUncaughtException")
	@Test
	public void testHandleUncaughtException() {
		Map<String, Object> errorResp = new HashMap<>();
		errorResp.put(MESSAGE_KEY, ERROR_MESSAGE);
		
		ResponseEntity<Object> expected = ResponseEntity.status(500).body(errorResp);
		
		ResponseEntity<Object> actual = ReflectionTestUtils.invokeMethod(this.globalExceptionConfig, "handleUncaughtException", mock(Exception.class), mock(WebRequest.class));
		
		assertEquals(expected, actual);
	}
}
