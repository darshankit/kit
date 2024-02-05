package com.kit.erp.twilio;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OtpRequest {
	@NotNull(message="emailOrPhone should not be empty")
	@NotEmpty(message="emailOrPhone should not be empty")
	private String emailOrPhone;
	private boolean status;
	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getEmailOrPhone() {
		return emailOrPhone;
	}

	public void setEmailOrPhone(String emailOrPhone) {
		this.emailOrPhone = emailOrPhone;
	}

	public OtpRequest(String emailOrPhone,boolean status) {
		super();
		this.status=status;
		this.emailOrPhone = emailOrPhone;
	}
    
	
    
}
