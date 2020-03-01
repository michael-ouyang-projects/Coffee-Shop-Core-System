package com.ouyang.goods;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long> {

	Goods findByName(String goodsName);
	
}
