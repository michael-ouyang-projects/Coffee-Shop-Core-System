package com.ouyang.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {

	@Autowired
	private StoreService storeService;

	@PostMapping("/store/create")
	public Integer createStore(@RequestBody Store store) {

		storeService.createStore(store);
		return 0;

	}

	@GetMapping("/store/query/{storeName}")
	public Store queryStoreByName(@PathVariable("storeName") String storeName) {

		return storeService.queryStoreByName(storeName);

	}

}