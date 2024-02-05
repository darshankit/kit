package com.kit.erp.twilio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OtpValidationRequest {
	@NotNull
	private String username;
    @Size(min = 6, max = 6, message = "OTP must contain 6 digits")
    @Pattern(regexp = "^\\d+$", message = "OTP must only contain digits")
	private String otpNumber;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOtpNumber() {
		return otpNumber;
	}
	public void setOtpNumber(String otpNumber) {
		this.otpNumber = otpNumber;
	}
	
	
}
