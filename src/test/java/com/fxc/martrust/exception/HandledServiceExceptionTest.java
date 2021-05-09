package com.fxc.martrust.exception;

class HandledServiceExceptionTest extends AbstractExceptionTest{
	
	@Override
	public Class<? extends Exception> getExceptionClass() {
		return HandledServiceException.class;
	}
}
