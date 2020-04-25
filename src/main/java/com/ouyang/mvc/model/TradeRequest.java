package com.ouyang.mvc.model;

import java.math.BigDecimal;
import java.util.List;

public class TradeRequest {

	private Long customerId;
	private Long branchId;
	private List<TradeGoods> buyingList;
	private BigDecimal totalPrice;

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

	public List<TradeGoods> getBuyingList() {
		return buyingList;
	}

	public void setBuyingList(List<TradeGoods> buyingList) {
		this.buyingList = buyingList;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
