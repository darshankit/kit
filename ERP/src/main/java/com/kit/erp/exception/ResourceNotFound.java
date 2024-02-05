package com.kit.erp.exception;

public class ResourceNotFound extends RuntimeException {
	String name;
	String field;
	String value;
	public ResourceNotFound(String name,String field, String value) {
		super(String.format("%s not found with %s, %s:",name,field,value));
		this.name=name;
		this.field=field;
		this.value=value;
		
	}
}
