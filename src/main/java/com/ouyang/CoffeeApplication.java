package com.ouyang;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouyang.branch.Branch;
import com.ouyang.branch.BranchService;

@SpringBootApplication
public class CoffeeApplication {

    @Autowired
    private BranchService branchService;

    public static void main(String[] args) {

        SpringApplication.run(CoffeeApplication.class, args);

    }

    @Bean
    public List<Branch> branches() {

        return branchService.queryAllBranches();

    }

    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper();

    }

}
