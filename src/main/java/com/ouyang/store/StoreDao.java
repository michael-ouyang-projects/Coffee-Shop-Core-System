package com.ouyang.store;

import org.springframework.stereotype.Repository;

@Repository
public class StoreDao {

	public void createStore(Store store) {
		
		
		
	}
	
	public Store queryStoreByName(String storeName) {
		
		Store store = new Store();
		store.setBranchName("HeadQuarter");
		store.setAddress("3 South Rockland Avenue Rome, NY 13440");
		
		return store;
		
	}
	
}
