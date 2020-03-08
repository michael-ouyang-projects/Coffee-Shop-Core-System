package com.ouyang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.branch.Branch;

@Controller
public class HomeController {

	@Autowired
	private ApplicationContext applicationContext;
	
	@GetMapping(value={"/", "/home"})
	public String home() {
		
		return "home.html";
		
	}
	
	@GetMapping("/branch-home")
	public String branchHome(@RequestParam("branchName") String branchName, Model model) {
		
		String welcomeMsg = String.format("== Welcome to %s ==", branchName);
		model.addAttribute("welcomeMsg", welcomeMsg);
		return "branch-home.html";
		
	}
	
//	@PostMapping("/store/create")
//	public Integer createStore(@RequestBody Branch store) {
//
//		storeService.createBranch(store);
//		return 0;
//
//	}
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("branches")
    public List<Branch> getStores() {
		
        return (List<Branch>) applicationContext.getBean("branches");
        
    }
	
}
