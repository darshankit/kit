package com.kit.erp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kit.erp.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class ErpApplication {

	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	
	
		
		
	}

}
