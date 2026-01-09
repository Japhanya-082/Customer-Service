package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.DTO.RestAPIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(DuplicateVendorException.class)
	    public ResponseEntity<RestAPIResponse> handleDuplicateVendor(DuplicateVendorException ex) {
	        return new ResponseEntity<>(
	                new RestAPIResponse("error", ex.getMessage(), null),
	                HttpStatus.CONFLICT // 409
	        );
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<RestAPIResponse> handleGeneral(Exception ex) {
	        return new ResponseEntity<>(
	                new RestAPIResponse("error", "Something went wrong!", null),
	                HttpStatus.INTERNAL_SERVER_ERROR // 500
	        );
	    }
}
