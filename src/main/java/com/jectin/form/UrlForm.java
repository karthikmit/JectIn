package com.jectin.form;

public class UrlForm {

	private String fullUrl = "";
	
	public UrlForm() {
		
	}
	
	public UrlForm(String fullurl) {
		this.fullUrl = fullurl;
	}

	public String getFullUrl() {
		return fullUrl ;
	}
	
	public void setFullUrl(String fullurl) {
		this.fullUrl = fullurl;
	}
}
