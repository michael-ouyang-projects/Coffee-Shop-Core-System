package com.ouyang.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	
	public List<Goods> queryGoodsByBranch(String id) {
		
		return goodsDao.queryGoodsByBranch(id);
		
	}
	
}
