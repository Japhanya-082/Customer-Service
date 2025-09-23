package com.example.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.CustomerDto;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
		
	}

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getById(Long customerId) {
		return customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
	}

	@Override
	public Customer updatedCustomer(Long customerId, Customer customer) {
	
	Customer oldCustomer = customerRepository.findById(customerId).map(existing ->{
		 existing.setFirstName(customer.getFirstName());
		 existing.setLastName(customer.getLastName());
		 existing.setEmail(customer.getEmail());
		 existing.setMoblieNumber(customer.getMoblieNumber());
		 existing.setBillingAddress(customer.getBillingAddress());
		 existing.setShippingAddress(customer.getShippingAddress());
		 existing.setTaxId(customer.getTaxId());
		 existing.setPaymentTerms(customer.getPaymentTerms());
		 existing.setPaymentMethod(customer.getPaymentMethod());
		 existing.setStatus(customer.getStatus());
		 return customerRepository.save(existing);
	 }).orElseThrow(()->new RuntimeException("cutomer not found with id"+customerId));
          return oldCustomer;
	}

	@Override
	public void deleteById(Long CustmoerId) {
	customerRepository.deleteById(CustmoerId);
	}

//	@Override
//	public CustomerDto toDto(Customer customer) {
//		return CustomerDto.builder()
//                .customerId(customer.getCustomerId())
//                .firstName(customer.getFirstName())
//                .lastName(customer.getLastName())
//                .email(customer.getEmail())
//                .moblieNumber(customer.getMoblieNumber())
//                .billingAddress(customer.getBillingAddress())
//                .shippingAddress(customer.getShippingAddress())
//                .status(customer.getStatus())
//                .build();
//    }

}
