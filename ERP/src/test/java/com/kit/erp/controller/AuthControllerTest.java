package com.kit.erp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

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
import com.kit.erp.twilio.OtpStatus;
import com.kit.erp.twilio.OtpValidationRequest;
import com.kit.erp.twilio.SmsService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
	@Mock
	private StudentService studentService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private EmailOtpService emailOtpService;

	@Mock
	private EmailService emailService;

	@Mock
	private SmsService smsService;

	@InjectMocks
	private AuthController authController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveStudent() {

		StudentDetails student = new StudentDetails();
		when(studentService.saveStudent(any(StudentDetails.class))).thenReturn(student);

		ResponseEntity<StudentDetails> response = authController.saveStudent(student);

		assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
		assertEquals(student, response.getBody());
		verify(studentService, times(1)).saveStudent(any(StudentDetails.class));
	}

	@Test
	void testLogin() {

		LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
		when(authenticationManager.authenticate(any())).thenReturn(null); // Simulate a successful authentication

		doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString()); // Simulate sending an
																									// email

		ResponseEntity<EmailOtpResponse> response = authController.login(loginRequest);

		assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
		assertEquals("otp sent to your mail", response.getBody().getMessage());
		verify(emailService, times(1)).sendSimpleMessage(anyString(), anyString(), anyString());
	}

	@Test
	void testValidateOtp() {
		// Given
		EmailOtpRequest otpRequest = new EmailOtpRequest("test@example.com", "123456");
		EmailOtp otp = new EmailOtp("test@example.com", "1234567",
				new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
		when(emailOtpService.getOtp(anyString())).thenReturn(otp);

		ResponseEntity<LoginResponse> response = authController.validateOtp(otpRequest);

		assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
		assertEquals("login successful", response.getBody().getStatus());
		verify(emailOtpService, times(1)).getOtp(anyString());
	}

	@Test
	void testSendOtp() {

		OtpRequest otpRequest = new OtpRequest("test@example.com", true);
		StudentDetails studentDetails = new StudentDetails();
		when(studentService.findById(anyString())).thenReturn(studentDetails);
		OtpResponse otpResponse = new OtpResponse(OtpStatus.DELIVERED, "OTP sent successfully");
		when(smsService.sendSMS(any(StudentDetails.class))).thenReturn(otpResponse);

		OtpResponse response = authController.sendOtp(otpRequest);

		assertEquals("OTP sent successfully", response.getMessage());
		verify(studentService, times(1)).findById(anyString());
		verify(smsService, times(1)).sendSMS(any(StudentDetails.class));
	}

	@Test
	void testValidateOtpSms() {

		OtpValidationRequest validationRequest = new OtpValidationRequest("test@example.com", "123456");
		when(smsService.validateOtp(any(OtpValidationRequest.class))).thenReturn("OTP validated successfully");

		ResponseEntity<String> response = authController.validateOtp(validationRequest);

		assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
		assertEquals("OTP validated successfully", response.getBody());
		verify(smsService, times(1)).validateOtp(any(OtpValidationRequest.class));
	}

	@Test
	void testUpdatePassword() {

		ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest("test@example.com", "newPassword");
		StudentDetails updatedStudentDetails = new StudentDetails();
		when(studentService.updatePassword(anyString(), anyString())).thenReturn(updatedStudentDetails);

		ResponseEntity<Map<String, String>> response = authController.updatePassword(forgetPasswordRequest);

		assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
		assertEquals("password updated", response.getBody().get("message"));
		verify(studentService, times(1)).updatePassword(anyString(), anyString());
	}
}
