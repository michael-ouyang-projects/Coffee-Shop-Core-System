package com.ouyang.mvc.model;

import java.math.BigDecimal;
import java.util.List;

public class TradeRequest {

	private Long customerId;
	private Long branchId;
	private BigDecimal totalPrice;
	private List<TradeGoods> buyingList;
	

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
