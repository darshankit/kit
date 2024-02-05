package com.kit.erp.entity.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmailOtpRequest {
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="invalid mail")
	private String email;
    @Size(min = 6, max = 6, message = "OTP must contain 6 digits")
    @Pattern(regexp = "^\\d+$", message = "OTP must only contain digits")
	private String otp;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
}
