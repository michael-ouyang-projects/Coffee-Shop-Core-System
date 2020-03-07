package com.ouyang.transaction.object;

import java.math.BigDecimal;
import java.util.List;

public class TradeResponse {

	private Long id;
	private BigDecimal totalPrice;
	private List<ResponseItem> items;

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

	public List<ResponseItem> getItems() {
		return items;
	}

	public void setItems(List<ResponseItem> items) {
		this.items = items;
	}

}
