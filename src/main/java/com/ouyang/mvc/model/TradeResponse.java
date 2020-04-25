package com.ouyang.mvc.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TradeResponse {

	private Long id;
	private Date tradeDate;
	private BigDecimal totalPrice;
	private List<TradeGoods> buyingList;

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

	public List<TradeGoods> getBuyingList() {
		return buyingList;
	}

	public void setBuyingList(List<TradeGoods> buyingList) {
		this.buyingList = buyingList;
	}

}
