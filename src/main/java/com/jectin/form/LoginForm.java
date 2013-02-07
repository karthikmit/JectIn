package com.jectin.form;

public class LoginForm {
	private String userMailId;
	private String password;
	
	public LoginForm() {
		
	}
	
	public LoginForm(String emailId, String password) {
		this.userMailId = emailId;
		this.password = password;
	}
	
	public String getUserMailId() {
		return userMailId;
	}
	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
