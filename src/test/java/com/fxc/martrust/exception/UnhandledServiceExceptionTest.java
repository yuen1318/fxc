package com.fxc.martrust.exception;

class UnhandledServiceExceptionTest extends AbstractExceptionTest{

	@Override
	public Class<? extends Exception> getExceptionClass() {
		return UnhandledServiceException.class;
	}

}
