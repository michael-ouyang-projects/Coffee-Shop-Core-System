package com.ouyang.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@GetMapping("/goods/{branchId}")
	public List<Goods> queryGoodsByBranch(@PathVariable("branchId") String id) {
		
		if("1".equals(id)) {
			
			return goodsService.queryGoodsByBranch(id);
			
		} else {
			
			return null;
			
		}
		
	}
	
}
