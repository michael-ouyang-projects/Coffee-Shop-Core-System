package com.ouyang.mvc.model;

import java.math.BigDecimal;

public class TradeCoffee {

	private Long coffeeId;
	private String coffeeName;
	private String size;
	private Integer amount;
	private BigDecimal price;
	private BigDecimal subtotal;

	public Long getCoffeeId() {
		return coffeeId;
	}

	public void setCoffeeId(Long coffeeId) {
		this.coffeeId = coffeeId;
	}

	public String getCoffeeName() {
		return coffeeName;
	}

	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

}
