package com.ouyang;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.ApplicationScope;

import com.ouyang.branch.Branch;
import com.ouyang.branch.BranchService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RetailStoreApplication {

	@Autowired
	private BranchService branchService;
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(RetailStoreApplication.class, args);
		
	}
	
	@ApplicationScope
	@Bean(value = "branches")
	public List<Branch> branches() {
		
		return branchService.queryAllBranches();
		
	}
	
	@Bean
	public Docket buildDocket() {
		
		return new Docket(DocumentationType.SWAGGER_2) 
		          .select()
		          .apis(RequestHandlerSelectors.any())
		          .paths(PathSelectors.any())
		          .build();
		
	}
	
}
