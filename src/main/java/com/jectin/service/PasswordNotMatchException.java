package com.jectin.service;

public class PasswordNotMatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8506484145673315992L;

	public PasswordNotMatchException(String message) {
		super(message);
	}
 }
