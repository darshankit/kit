package com.kit.erp.twilio;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kit.erp.config.TwilioConfig;
import com.kit.erp.email.EmailOtp;
import com.kit.erp.email.EmailOtpService;
import com.kit.erp.entity.StudentDetails;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	@Autowired
	private TwilioConfig twilioConfig;

	@Autowired
	private EmailOtpService emailRepo;

	Map<String, String> otpMap = new HashMap<>();

	public OtpResponse sendSMS(StudentDetails otpRequest) {
		OtpResponse otpResponse = null;
		try {
			PhoneNumber to = new PhoneNumber("+"+otpRequest.getMobileNumber());// to
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
			String otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is  " + otp
					+ " for sending sms through Spring boot application. Thank You.";
			//Message message = Message.creator(to, from, otpMessage).create();

			EmailOtp twilio = new EmailOtp();
			twilio.setEmail(otpRequest.getEmail());
			twilio.setOtp(otp);
			twilio.setExpirationTime(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
			System.out.println(twilio.getEmail());
			System.out.println(twilio.getOtp());
			this.emailRepo.saveOtp(twilio);

			otpResponse = new OtpResponse(OtpStatus.DELIVERED, otpMessage);
		} catch (Exception e) {
			e.printStackTrace();
			otpResponse = new OtpResponse(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponse;
	}

	public String validateOtp(OtpValidationRequest otpValidationRequest) {

		EmailOtp otp = emailRepo.getOtp(otpValidationRequest.getUsername());
		System.out.println("otp: " + otp);

		if (otpValidationRequest.getOtpNumber().equals(otp.getOtp()) && !otp.getExpirationTime().before(new Date())) {
			emailRepo.deleteOtp(otpValidationRequest.getUsername());
			return "OTP is valid!";
		} else {
			return "OTP is invalid!";
		}
	}
	

	private String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}
}
