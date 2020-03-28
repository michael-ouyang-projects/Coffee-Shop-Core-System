package com.ouyang.mvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.branch.BranchService;

@Controller
public class HomeController {

	@Autowired
	private BranchService branchService;

	@GetMapping(value = { "/", "/home" })
	public String home() {

		return "home.html";

	}

	@PostMapping("/branch-home")
	public String branchHome(@RequestParam("branchName") String branchName, 
							 HttpSession session) {

		session.setAttribute("branch", branchService.queryByName(branchName));
		return "branch-home.html";

	}

}
