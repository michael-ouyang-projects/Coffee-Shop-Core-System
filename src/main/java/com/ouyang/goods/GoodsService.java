package com.ouyang.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;
	
	public Goods queryGoodsByName(String goodsName) {
		
		return goodsRepository.findByName(goodsName);
		
	}
	
}
