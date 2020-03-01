package com.ouyang.store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

	Store findByBranchName(String branchName);
	
	String findBranchNameById(Long Id);
	
}
