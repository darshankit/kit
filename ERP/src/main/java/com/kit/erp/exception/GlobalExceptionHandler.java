package com.kit.erp.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<ResponseApi> resourceNotFound(ResourceNotFound ex){
		ResponseApi api=new ResponseApi(ex.getLocalizedMessage(), false);
		return new ResponseEntity<ResponseApi>(api,HttpStatusCode.valueOf(404));
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> validationException(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
		return new ResponseEntity<>(errors, HttpStatusCode.valueOf(400));
	}
	
	@ExceptionHandler
	public ResponseEntity<Map<String,String>> constraintViolationException(ConstraintViolationException ex){
		Map<String, String> map=new HashMap<>();
		ex.getConstraintViolations().forEach(err->{
			map.put(err.getPropertyPath().toString(), err.getMessage());
		});
		ResponseApi api=new ResponseApi(ex.getLocalizedMessage(), false);
		return new ResponseEntity<Map<String,String>>(map,HttpStatusCode.valueOf(404));
	}
}
