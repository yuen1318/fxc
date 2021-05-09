package com.fxc.martrust.exception;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

public abstract class AbstractExceptionTest {

	private static final String MESSAGE = "test-message";
	private static final IllegalArgumentException CAUSE = new IllegalArgumentException();

	private final Class<? extends Exception> exceptionClazz;

	protected abstract Class<? extends Exception> getExceptionClass();

	public AbstractExceptionTest() {
		this.exceptionClazz = this.getExceptionClass();
	}

	@Test
	public void testNoArgConstructor()
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Throwable throwable = this.exceptionClazz.getConstructor().newInstance();
		assertNotNull(throwable);
		assertNull(throwable.getMessage());
		assertNull(throwable.getCause());
	}

	@Test
	public void testMessageAndCause()
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Throwable throwable = this.exceptionClazz.getConstructor(String.class, Throwable.class).newInstance(MESSAGE,
				CAUSE);
		assertNotNull(throwable);
		assertEquals(MESSAGE, throwable.getMessage());
		assertEquals(CAUSE, throwable.getCause());
	}

	@Test
	public void testMessage()
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Throwable throwable = this.exceptionClazz.getConstructor(String.class).newInstance(MESSAGE);
		assertNotNull(throwable);
		assertNull(throwable.getCause());
		assertEquals(MESSAGE, throwable.getMessage());
	}

	@Test
	public void testCause()
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Throwable throwable = this.exceptionClazz.getConstructor(Throwable.class).newInstance(CAUSE);
		assertNotNull(throwable);
		assertEquals(CAUSE.toString(), throwable.getMessage());
		assertEquals(CAUSE, throwable.getCause());
	}

}


