package com.ouyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;

@Controller
public class TradeController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/trade-home")
	public String branchHome(@RequestParam("customerId") Long customerId, Model model) {

		Customer customer = customerService.queryCustomerById(customerId);
		model.addAttribute("customerId", String.format("Customer Id: %d", customer.getId()));
		model.addAttribute("customerName", String.format("Customer Name: %s", customer.getName()));
		return "trade-home.html";

	}

}
