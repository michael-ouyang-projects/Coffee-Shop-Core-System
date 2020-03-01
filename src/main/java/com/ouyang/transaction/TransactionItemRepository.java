package com.ouyang.transaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionItemRepository extends CrudRepository<TransactionItem, Long> {

	
	
}
