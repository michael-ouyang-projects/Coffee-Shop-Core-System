package com.ouyang.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@GetMapping("/goods/{id}")
	public Goods queryGoodsById(@PathVariable("id") Long id) {

		return goodsService.queryGoodsById(id);
		
	}
	
}
