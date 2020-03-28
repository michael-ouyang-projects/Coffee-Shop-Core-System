package com.ouyang;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ouyang.branch.Branch;
import com.ouyang.branch.BranchService;

@SpringBootApplication
public class RetailStoreApplication {

	@Autowired
	private BranchService branchService;

	public static void main(String[] args) {

		SpringApplication.run(RetailStoreApplication.class, args);

	}

	@Bean(value = "branches")
	public List<Branch> branches() {

		return branchService.queryAllBranches();

	}

}
