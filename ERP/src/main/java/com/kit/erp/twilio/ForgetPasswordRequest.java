package com.kit.erp.twilio;

import jakarta.validation.constraints.Pattern;

public class ForgetPasswordRequest {
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="invalid mail")
	private String email;
	@Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$",message = "password should contain Minimum 8 characters, at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
