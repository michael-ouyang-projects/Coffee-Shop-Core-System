package com.ouyang.transaction;

import java.math.BigDecimal;
import java.util.List;

public class TradeResponse {

	private Long id;
	private BigDecimal totalPrice;
	private List<ItemResponse> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStoreBranchName() {
		return storeBranchName;
	}

	public void setStoreBranchName(String storeBranchName) {
		this.storeBranchName = storeBranchName;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ItemResponse> getItems() {
		return items;
	}

	public void setItems(List<ItemResponse> items) {
		this.items = items;
	}

}
