package com.ouyang.store;

import java.util.List;

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
	
	
	public List<Store> queryAllStores() {
		
		return (List<Store>) storeRepository.findAll();
		
	}
	
}
