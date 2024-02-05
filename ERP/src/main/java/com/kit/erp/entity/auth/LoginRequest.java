package com.kit.erp.entity.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {
	
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="invalid mail")
	private String email;
	@Size(min = 6, max = 20, message="Password requirement not matching")
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
