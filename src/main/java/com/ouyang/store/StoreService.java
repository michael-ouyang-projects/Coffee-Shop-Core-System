package com.ouyang.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public void createStore(Store store) {
		
		storeRepository.save(store);
		
	}
	
	public Store queryStoreByName(String branchName) {
		
		return storeRepository.findByBranchName(branchName);
		
	}
	
}
