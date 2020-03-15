package com.ouyang.transaction.object;

import java.math.BigDecimal;
import java.util.List;

import com.ouyang.mvc.model.BuyingGoods;

public class TradeRequest {

	private Long customerId;
	private Long branchId;
	private List<BuyingGoods> buyingGoodsList;
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

	public List<BuyingGoods> getBuyingGoodsList() {
		return buyingGoodsList;
	}

	public void setBuyingGoodsList(List<BuyingGoods> buyingGoodsList) {
		this.buyingGoodsList = buyingGoodsList;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
