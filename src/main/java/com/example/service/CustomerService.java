package com.example.service;

import java.util.List;


import com.example.entity.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer); 
	public List<Customer> getAll();
	public Customer getById(Long customerId);
	public Customer updatedCustomer(Long customerId, Customer customer);
	public void deleteById(Long CustmoerId);
	
//	  public  CustomerDto toDto(Customer customer);
}
