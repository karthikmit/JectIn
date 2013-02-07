package com.jectin.service;

public class UserNotExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3145854909515920011L;

	public UserNotExistsException(String message) {
		super(message);
	}
}
