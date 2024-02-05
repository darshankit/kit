package com.kit.erp.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class StudentDetails implements UserDetails{
	@Id
	@Size(min=6, max=10, message = "erp size should be in between 6 and 10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message = "ERP should not contain any special characters")
	private String erp;
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]+$", message = "FirstName should contain only alphabets")
	private String firstName;
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]+$", message = "LastName should contain only alphabets")
	private String lastName;
	@NotNull(message = "gender should not be empty")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "gender should contain only alphabets")
	private String gender;
	@Size(max=13)
	@Pattern(regexp = "^\\+\\d+$", message="invalid phone number")
	private String mobileNumber;
	@NotNull(message = "parents name cannot be null")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Parents Name should contain only alphabets")
	private String parentsName;
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message="invalid mail")
	private String email;
	@Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$",message = "password should contain Minimum 8 characters, at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;
	public String getErp() {
		return erp;
	}
	public void setErp(String erp) {
		this.erp = erp;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getParentsName() {
		return parentsName;
	}
	public void setParentsName(String parentsName) {
		this.parentsName = parentsName;
	}
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
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getEmail();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
}
