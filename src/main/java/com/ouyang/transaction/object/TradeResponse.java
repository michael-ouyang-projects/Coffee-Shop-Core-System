package com.ouyang.transaction.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ouyang.mvc.model.BuyingGoods;

public class TradeResponse {

	private Long id;
	private Date tradeDate;
	private BigDecimal totalPrice;
	private List<BuyingGoods> buyingList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<BuyingGoods> getBuyingList() {
		return buyingList;
	}

	public void setBuyingList(List<BuyingGoods> buyingList) {
		this.buyingList = buyingList;
	}

}
