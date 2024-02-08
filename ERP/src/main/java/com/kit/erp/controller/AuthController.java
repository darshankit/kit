package com.kit.erp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kit.erp.email.EmailOtp;
import com.kit.erp.email.EmailOtpService;
import com.kit.erp.email.EmailService;
import com.kit.erp.entity.StudentDetails;
import com.kit.erp.entity.auth.EmailOtpRequest;
import com.kit.erp.entity.auth.EmailOtpResponse;
import com.kit.erp.entity.auth.LoginRequest;
import com.kit.erp.entity.auth.LoginResponse;

import com.kit.erp.service.StudentService;
import com.kit.erp.twilio.ForgetPasswordRequest;
import com.kit.erp.twilio.OtpRequest;
import com.kit.erp.twilio.OtpResponse;
import com.kit.erp.twilio.OtpValidationRequest;
import com.kit.erp.twilio.SmsService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private StudentService studentService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailOtpService emailOtpService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SmsService smsService;
	
	@GetMapping("/allStudents")
	public ResponseEntity<List<StudentDetails>> findAll(){
		List<StudentDetails> all = studentService.findAll();
		
		return ResponseEntity.ok(all);
	}

	// This mapping is to register a student details
	@PostMapping("/save")
	public ResponseEntity<StudentDetails> saveStudent(@Valid @RequestBody StudentDetails student) {
		StudentDetails studentDetails = studentService.saveStudent(student);
		return ResponseEntity.ok(studentDetails);
	}

	// This mapping is to compare the username and password which are in database
	// with the entered username and password.
	@RequestMapping("/login")
	public ResponseEntity<EmailOtpResponse> login(@Valid @RequestBody LoginRequest login) {
		EmailOtpResponse response;
		int code;

		boolean f = this.doAuthentication(login.getEmail(), login.getPassword());// calling doAuthentication method to
																					// authenticating username and
																					// password
		if (f) {
			code = 200;
			String otp = this.generateOTP(login.getEmail());// calling generateOTP method to generate random OTP.
			String to = login.getEmail();
			String subject = "OTP For login";
			String message = "This is the 6 digit OTP for login " + otp + " \n Don't Share this with anyone";
			emailService.sendSimpleMessage(to, subject, message);
			response = new EmailOtpResponse("otp sent to your mail", otp);
		} else {
			response = new EmailOtpResponse("authentication failed", "201");
			code = 201;
		}
		return new ResponseEntity<EmailOtpResponse>(response, HttpStatusCode.valueOf(code));

	}

	@RequestMapping("/otp-validation")
	public ResponseEntity<LoginResponse> validateOtp(@Valid @RequestBody EmailOtpRequest otpRequest) {
		EmailOtp otp = emailOtpService.getOtp(otpRequest.getEmail());
		LoginResponse response;
		if (otpRequest.getOtp().equals(otp.getOtp()) && !otp.getExpirationTime().before(new Date())) {
			response = new LoginResponse("login successful", "200");
			System.out.println("login successful");
			emailOtpService.deleteOtp(otpRequest.getEmail());
		} else {
			response = new LoginResponse("invalid Otp", "201");
			System.out.println("login failed");
		}
		return ResponseEntity.ok(response);

	}

	@PutMapping("/demo")
	public ResponseEntity<Map<String, String>> demo(@RequestBody ForgetPasswordRequest request) {
		Map<String, String> m = new HashMap<>();
		m.put("name", "darshan");
		m.put("place", "kadur");
		return ResponseEntity.ok(m);
	}

	@RequestMapping("/send-otp")
	public OtpResponse sendOtp(@Valid @RequestBody OtpRequest otpRequest) {

		StudentDetails studentDetails = studentService.findById(otpRequest.getEmailOrPhone());
		OtpResponse sendSMS = smsService.sendSMS(studentDetails);

		return sendSMS;
	}

	@RequestMapping("/validate-otp")
	public ResponseEntity<String> validateOtp(@Valid @RequestBody OtpValidationRequest validation) {
		String validateOtp = smsService.validateOtp(validation);
		Map<String, String> map = new HashMap<>();
		map.put("message", validateOtp);
		return ResponseEntity.ok(validateOtp);

	}

	@RequestMapping("/update-password")
	public ResponseEntity<Map<String, String>> updatePassword(
			@Valid @RequestBody ForgetPasswordRequest forgetPassword) {
		studentService.updatePassword(forgetPassword.getPassword(), forgetPassword.getEmail());
		Map<String, String> map = new HashMap<>();
		map.put("message", "password updated");
		return ResponseEntity.ok(map);
	}

	public boolean doAuthentication(String email, String password) {
		boolean flag = false;
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			authenticationManager.authenticate(authentication);
			flag = true;
		} catch (BadCredentialsException ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return flag;

	}

	public String generateOTP(String email) {
		Random random = new Random();
		int otpValue = 100_000 + random.nextInt(900_000);
		String otp = String.valueOf(otpValue);
		EmailOtp e = new EmailOtp();
		e.setEmail(email);
		e.setOtp(otp);
		e.setExpirationTime(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
		emailOtpService.saveOtp(e);

		// Here you would save the OTP and associated email to your database
		
		return otp;
	}

}
