package com.ouyang.transaction.object;

import java.math.BigDecimal;
import java.util.List;

import com.ouyang.mvc.model.BuyingGoods;

public class TradeRequest {

	private Long customerId;
	private Long branchId;
	private List<BuyingGoods> buyingList;
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

	public List<BuyingGoods> getBuyingList() {
		return buyingList;
	}

	public void setBuyingList(List<BuyingGoods> buyingList) {
		this.buyingList = buyingList;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
