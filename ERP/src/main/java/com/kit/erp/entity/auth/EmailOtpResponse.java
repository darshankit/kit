package com.kit.erp.entity.auth;

public class EmailOtpResponse {
	private String message;
	private String otp;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public EmailOtpResponse(String message, String otp) {
		super();
		this.message = message;
		this.otp = otp;
	}
	
	
	
	
}
