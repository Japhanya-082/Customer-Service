package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.CustomerDto;
import com.example.DTO.RestAPIResponse;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.serviceImpl.CustomerServiceImpl;



@RestController
@RequestMapping("/customer")
public class CustomerController {

  

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

  
	
	@PostMapping("/save")
	public ResponseEntity<RestAPIResponse> saveCustomerData(@RequestBody Customer customer) {
	
		try {
			 return new ResponseEntity<>(new RestAPIResponse("success","Registered Successfully",customerServiceImpl.createCustomer(customer)),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RestAPIResponse("error","registerd failed"),HttpStatus.OK);
		}
		
	}
	@GetMapping("/getall")
	public ResponseEntity<RestAPIResponse> allCustomers(){
		try {
			 return new ResponseEntity<>(new RestAPIResponse("success","User Data getting Successfully",customerServiceImpl.getAll()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RestAPIResponse("error","User Data is Null"),HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<RestAPIResponse> getById(@PathVariable Long customerId,@RequestBody Customer customer) {
		try {
	        RestAPIResponse response = new RestAPIResponse("success","User Data getting by Id Successfully", customerServiceImpl.getById(customerId, customer) );
	        return new ResponseEntity<RestAPIResponse>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        RestAPIResponse response = new RestAPIResponse("error", "User is not found");
	        return new ResponseEntity<RestAPIResponse>(response, HttpStatus.OK);
	    }
	}
	
@PutMapping("/{customerId}")
public ResponseEntity<RestAPIResponse> updateById(@PathVariable Long customerId, @RequestBody Customer customer) {
	try {
        RestAPIResponse response = new RestAPIResponse("success",  "User Data Updated Successfully", customerServiceImpl.updatedCustomer(customerId, customer));
        return new ResponseEntity<RestAPIResponse>(response, HttpStatus.OK);
    } catch (Exception e) {
        RestAPIResponse response = new RestAPIResponse("error", "User Data is not Updated" );
        return new ResponseEntity<RestAPIResponse>(response, HttpStatus.OK);
    }
	
}
@DeleteMapping("/{customerId}")
public ResponseEntity<RestAPIResponse> deleteCustomer(@PathVariable Long customerId) {
    try {
        customerServiceImpl.deleteById(customerId); 
        RestAPIResponse response = new RestAPIResponse(  "success",  "Deleted Successfully" );
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        RestAPIResponse response = new RestAPIResponse( "error", "User Data is Not Deleted" );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


}