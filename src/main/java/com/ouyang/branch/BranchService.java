package com.ouyang.branch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BranchService {
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public void createBranch(Branch branch) {
		
		branchRepository.save(branch);
		
	}
	
	
	public List<Branch> queryAllBranches() {
		
		return (List<Branch>) branchRepository.findAll();
		
	}
	
	public Branch queryByName(String name) {
		
		return branchRepository.findByName(name);
		
	}
	
}
