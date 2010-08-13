package com.hellocode.exception;

public class NetProblem extends Exception {

	@Override
	public String getMessage() {
		
		return "ÍøÂçÒì³£";
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	@Override
	public String toString() {
		return super.toString()+"ÍøÂçÒì³£";
	}
	
}
