package com.rondoll.sc2replayrenamer;

public class RuleException extends Exception {
	private String message;
	
	public RuleException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
