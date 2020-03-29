package com.ouyang.mvc.controller.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.mvc.model.ReturningGoods;
import com.ouyang.transaction.TransactionService;
import com.ouyang.transaction.object.Transaction;
import com.ouyang.transaction.object.TransactionItem;

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
		session.setAttribute("returningList", new ArrayList<ReturningGoods>());

	}

	public ReturningGoods getReturningGoodsFromReturningListByGoodsId(List<ReturningGoods> returningList, Long goodsId) {

		return returningList
				.stream()
				.filter(returningGoods -> returningGoods.getId().equals(goodsId))
				.findFirst()
				.orElse(null);

	}

	public void setNewAmountAndSubtotalForExistReturningGoods(ReturningGoods returningGoods, Integer amount) {

		int newReturningAmount = returningGoods.getAmount() + amount;
		returningGoods.setAmount(newReturningAmount);
		returningGoods.setSubtotal(returningGoods.getPrice().multiply(new BigDecimal(newReturningAmount)));

	}

	public void addReturningGoodsToReturningList(List<ReturningGoods> returningList, Transaction transaction, Long goodsId, Integer amount) {

		TransactionItem transactionItem = this.getTransactionItemByGoodsId(transaction, goodsId);

		ReturningGoods returningGoods = new ReturningGoods();
		returningGoods.setId(goodsId);
		returningGoods.setName(transactionItem.getName());
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

	public BigDecimal calculateTotalReturningPrice(List<ReturningGoods> returningList) {

		return returningList
				.stream()
				.map(ReturningGoods::getSubtotal)
				.reduce(BigDecimal::add)
				.get();

	}

}
