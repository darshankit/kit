package com.kit.erp.service;

import java.util.List;

import com.kit.erp.entity.StudentDetails;




public interface StudentService {
	public StudentDetails saveStudent(StudentDetails student);
	public StudentDetails updatePassword(String password,String email);
	public StudentDetails findById(String otpRequest);
	public List<StudentDetails> findAll();
}
