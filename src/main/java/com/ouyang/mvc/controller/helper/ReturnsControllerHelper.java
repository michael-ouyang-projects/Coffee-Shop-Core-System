package com.ouyang.mvc.controller.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.mvc.model.TradeGoods;
import com.ouyang.transaction.Transaction;
import com.ouyang.transaction.TransactionItem;
import com.ouyang.transaction.TransactionService;

@Component
public class ReturnsControllerHelper {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CustomerService customerService;

	public void initSessionDataForReturnsRequest(Long tradeId, HttpSession session) {

		Transaction transaction = transactionService.queryTrade(tradeId);
		Customer customer = customerService.queryCustomerById(transaction.getCustomerId());

		session.setAttribute("transaction", transaction);
		session.setAttribute("customer", customer);
		session.setAttribute("returningList", new ArrayList<TradeGoods>());

	}

	public TradeGoods getReturningGoodsFromReturningListByGoodsId(List<TradeGoods> returningList, Long goodsId) {

		return returningList
				.stream()
				.filter(returningGoods -> returningGoods.getId().equals(goodsId))
				.findFirst()
				.orElse(null);

	}

	public void setNewAmountAndSubtotalForExistReturningGoods(TradeGoods returningGoods, Integer amount) {

		int newReturningAmount = returningGoods.getAmount() + amount;
		returningGoods.setAmount(newReturningAmount);
		returningGoods.setSubtotal(returningGoods.getPrice().multiply(new BigDecimal(newReturningAmount)));

	}

	public void addReturningGoodsToReturningList(List<TradeGoods> returningList, Transaction transaction, Long goodsId, Integer amount) {

		TransactionItem transactionItem = this.getTransactionItemByGoodsId(transaction, goodsId);

		TradeGoods returningGoods = new TradeGoods();
		returningGoods.setId(goodsId);
		returningGoods.setName(transactionItem.getGoodsName());
		returningGoods.setAmount(amount);
		BigDecimal goodsPrice = transactionItem.getSubtotal().divide(new BigDecimal(transactionItem.getAmount()));
		returningGoods.setPrice(goodsPrice);
		returningGoods.setSubtotal(goodsPrice.multiply(new BigDecimal(amount)));

		returningList.add(returningGoods);

	}

	private TransactionItem getTransactionItemByGoodsId(Transaction transaction, Long goodsId) {

		return transaction
				.getItems()
				.stream()
				.filter(item -> item.getGoodsId().equals(goodsId))
				.findFirst()
				.get();

	}

	public BigDecimal calculateTotalReturningPrice(List<TradeGoods> returningList) {

		return returningList
				.stream()
				.map(TradeGoods::getSubtotal)
				.reduce(BigDecimal::add)
				.get();

	}

}
