package com.ouyang.transaction.object;

import java.util.List;

public class TradeRequest {

	private Long customerId;
	private Long storeId;
	private List<RequestItem> items;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public List<RequestItem> getItems() {
		return items;
	}

	public void setItems(List<RequestItem> items) {
		this.items = items;
	}

}
