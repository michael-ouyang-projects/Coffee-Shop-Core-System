package com.ouyang.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreService {
	
	@Autowired
	private StoreDao storeDao;
	
	@Transactional(rollbackFor = Exception.class)
	public void createStore(Store store) {
		
		storeDao.createStore(store);
		
	}
	
	public Store queryStoreByName(String storeName) {
		
		return storeDao.queryStoreByName(storeName);
		
	}
	
}
