package com.ouyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@Autowired
	private ApplicationContext applicationContext;
	
	@GetMapping(value={"/", "/home"})
	public String home(Model model) {
		
		model.addAttribute("branches", applicationContext.getBean("branches"));
		return "home.html";
		
	}
	
	@PostMapping("/branch-home")
	public String branchHome(@RequestParam("branchName") String branchName, Model model) {
		
		String welcomeMessage = String.format("== Welcome to %s ==", branchName);
		model.addAttribute("welcomeMessage", welcomeMessage);
		return "branch-home.html";
		
	}
		
}
