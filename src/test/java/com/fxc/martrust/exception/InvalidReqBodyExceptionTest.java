package com.fxc.martrust.exception;

class InvalidReqBodyExceptionTest extends AbstractExceptionTest{

	@Override
	public Class<? extends Exception> getExceptionClass() {
		return InvalidReqBodyException.class;
	}

}
