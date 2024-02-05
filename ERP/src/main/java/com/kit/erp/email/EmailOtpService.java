package com.kit.erp.email;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kit.erp.exception.ResourceNotFound;

@Service
public class EmailOtpService {
	@Autowired
	private EmailOtpRepo emailOtpRepo;
	
	
	public void saveOtp(EmailOtp emailOtp) {
		System.out.println(emailOtp.getEmail());
		EmailOtp save = emailOtpRepo.save(emailOtp);
		System.out.println(save);
	}
	
	public EmailOtp getOtp(String email) {
		 EmailOtp emailOtp= emailOtpRepo.findById(email).orElseThrow(()->new ResourceNotFound("OTP", "email", email));
		 System.out.println(emailOtp);
		return emailOtp;
	}
	
	public void deleteOtp(String email) {
		EmailOtp emailOtp= emailOtpRepo.findById(email).orElseThrow(null);
		emailOtpRepo.delete(emailOtp);
	}
}
