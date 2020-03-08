package com.ouyang;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ouyang.branch.Branch;
import com.ouyang.branch.BranchService;

@Configuration
public class BranchConfig {

	@Autowired
	private BranchService branchService;
	
	@Bean
	public List<Branch> branches() {
		
		List<Branch> branches = branchService.queryAllBranches();
		return branches;
		
	}
	
}
