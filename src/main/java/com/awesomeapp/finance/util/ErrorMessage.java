package com.awesomeapp.finance.util;

public class ErrorMessage {
	private String errorMessage;
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
		System.out.println(this.errorMessage);
	}
	
}
