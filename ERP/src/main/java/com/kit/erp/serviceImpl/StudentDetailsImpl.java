package com.kit.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.kit.erp.entity.StudentDetails;
import com.kit.erp.exception.ResourceNotFound;
import com.kit.erp.repo.StudentRepo;
import com.kit.erp.service.StudentService;
import com.kit.erp.twilio.OtpRequest;

@Service
public class StudentDetailsImpl implements StudentService {
	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private BCryptPasswordEncoder bcry;

	@Override
	public StudentDetails saveStudent(StudentDetails student) {
		student.setPassword(bcry.encode(student.getPassword()));
		StudentDetails s = studentRepo.save(student);

		return s;
	}

	@Override
	public StudentDetails updatePassword(String password, String email) {
		StudentDetails details = studentRepo.findByEmail(email).orElseThrow(()->new ResourceNotFound("user", "emailu", email));
		details.setPassword(bcry.encode(password));
		StudentDetails studentDetails = studentRepo.save(details);
		return studentDetails;
	}

	@Override
	public StudentDetails findById(String otpRequest) {
		// TODO Auto-generated method stub
		StudentDetails student=null;
		if(!otpRequest.contains("@")) {
			 student= studentRepo.findByMobileNumber(otpRequest).orElseThrow(()->new ResourceNotFound("user", "phone number", otpRequest));
			
		}
		else {
			 student=studentRepo.findByEmail(otpRequest).orElseThrow(()->new ResourceNotFound("user", "email", ""+otpRequest));
		}
		return student;
	}

	@Override
	public List<StudentDetails> findAll() {
		List<StudentDetails> all = studentRepo.findAll();
		return all;
	}

}
