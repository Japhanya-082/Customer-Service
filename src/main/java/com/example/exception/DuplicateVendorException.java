package com.example.exception;

public class DuplicateVendorException extends RuntimeException{

	public DuplicateVendorException(String message) {
        super(message);
    }
}
