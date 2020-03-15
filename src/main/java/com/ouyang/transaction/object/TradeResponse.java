package com.ouyang.transaction.object;

import java.math.BigDecimal;
import java.util.List;

import com.ouyang.mvc.model.BuyingGoods;

public class TradeResponse {

	private Long id;
	private BigDecimal totalPrice;
	private List<BuyingGoods> buyingGoodsList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<BuyingGoods> getBuyingGoodsList() {
		return buyingGoodsList;
	}

	public void setBuyingGoodsList(List<BuyingGoods> buyingGoodsList) {
		this.buyingGoodsList = buyingGoodsList;
	}

}
