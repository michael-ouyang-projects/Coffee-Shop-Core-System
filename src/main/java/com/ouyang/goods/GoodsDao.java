package com.ouyang.goods;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class GoodsDao {

	public List<Goods> queryGoodsByBranch(String id) {

		List<Goods> goodsList = new ArrayList<>();

		Goods goods1 = new Goods();
		goods1.setName("Borden Milk");
		goods1.setPrice(new BigDecimal(3.5));
		goods1.setType("Food");
		goodsList.add(goods1);
		
		Goods goods2 = new Goods();
		goods2.setName("Organic Valley Milk");
		goods2.setPrice(new BigDecimal(4));
		goods2.setType("Food");
		goodsList.add(goods2);

		return goodsList;

	}

}
