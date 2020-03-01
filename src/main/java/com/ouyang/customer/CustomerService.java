package com.ouyang.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer queryCustomerById(Long id) {
		
		return customerRepository.findById(id).get();
		
	}
	
}
