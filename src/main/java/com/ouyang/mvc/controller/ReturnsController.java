package com.ouyang.mvc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.mvc.model.ReturningGoods;
import com.ouyang.transaction.TransactionService;
import com.ouyang.transaction.object.Transaction;
import com.ouyang.transaction.object.TransactionItem;

@Controller
public class ReturnsController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CustomerService customerService;

	@PostMapping("/returns-home")
	public String returnsHome(@RequestParam("tradeId") Long tradeId, 
							  HttpSession session) {

		Transaction transaction = transactionService.queryTrade(tradeId);
		Customer customer = customerService.queryCustomerById(transaction.getCustomerId());

		session.setAttribute("transaction", transaction);
		session.setAttribute("customer", customer);
		session.setAttribute("returningGoodsList", new ArrayList<ReturningGoods>());

		return "returns/returns-home.html";

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/add-returning-goods")
	public String addReturningGoods(@RequestParam("goodsId") Long goodsId,
									@RequestParam("number") Integer number,
									HttpSession session) {

		List<ReturningGoods> returningGoodsList = (List<ReturningGoods>) session.getAttribute("returningGoodsList");
		Transaction transaction = (Transaction) session.getAttribute("transaction");

		ReturningGoods returningGoods = null;
		if ((returningGoods = getReturningGoodsIfExist(returningGoodsList, goodsId)) != null) {

			returningGoods.setAmount(returningGoods.getAmount() + number);

		} else {

			TransactionItem transactionItem = transaction.getItems().stream().filter(item -> item.getGoodsId().equals(goodsId)).findFirst().get();
			returningGoods = new ReturningGoods();
			returningGoods.setId(goodsId);
			returningGoods.setName(transactionItem.getName());
			returningGoods.setAmount(number);
			returningGoods.setSubtotal(transactionItem.getSubtotal());
			returningGoodsList.add(returningGoods);

		}

		session.setAttribute("totalReturningPrice", returningGoodsList.stream().map(ReturningGoods::getPrice).reduce(BigDecimal::add).get());
		return "returns/returns-home.html";

	}

	private ReturningGoods getReturningGoodsIfExist(List<ReturningGoods> returningGoodsList, Long goodsId) {

		return returningGoodsList.stream().filter(returningGoods -> returningGoods.getId().equals(goodsId)).findFirst().orElse(null);

	}

}
