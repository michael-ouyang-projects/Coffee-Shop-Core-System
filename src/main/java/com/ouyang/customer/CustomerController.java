package com.ouyang.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customer/{id}")
	public Customer queryCustomerById(@PathVariable("id") Long id) {
		
		return customerService.queryCustomerById(id);
		
	}
	
}
