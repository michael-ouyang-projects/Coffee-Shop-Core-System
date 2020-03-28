package com.ouyang.transaction.object;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TransactionItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(targetEntity = Transaction.class)
	@JoinColumn(name = "TRANSACTIONID")
	private Transaction transaction;
	private Long goodsId;
	private String goodsName;
	private Integer goodsNumber;
	private BigDecimal subtotal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal goodsPrice) {
		this.subtotal = goodsPrice;
	}

}
