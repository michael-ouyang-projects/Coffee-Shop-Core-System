package com.ouyang.transaction.object;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long customerId;
	private Long storeId;
	private BigDecimal totalPrice;
	@OneToMany(targetEntity = TransactionItem.class, mappedBy = "transaction", cascade = CascadeType.ALL)
	private List<TransactionItem> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<TransactionItem> getItems() {
		return items;
	}

	public void setItems(List<TransactionItem> items) {
		this.items = items;
	}

}
