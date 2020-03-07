package com.ouyang.transaction.object;

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
