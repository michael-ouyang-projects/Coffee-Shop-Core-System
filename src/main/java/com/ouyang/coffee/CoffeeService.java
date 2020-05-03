package com.ouyang.coffee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

	@Autowired
	private CoffeeRepository coffeeRepository;
	
	public Coffee queryCoffeeById(Long id) {
		
		return coffeeRepository.findById(id).get();
		
	}
	
}
