package com.ouyang.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.mvc.controller.helper.ReturnsControllerHelper;
import com.ouyang.mvc.model.ReturningGoods;
import com.ouyang.transaction.object.Transaction;

@Controller
public class ReturnsController {

	@Autowired
	private ReturnsControllerHelper helper;

	
	@PostMapping("/returns-home")
	public String returnsHome(@RequestParam("tradeId") Long tradeId, 
							  HttpSession session) {

		helper.initSessionDataForReturnsRequest(tradeId, session);
		return "returns/returns-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/add-returning-goods")
	public String addReturningGoods(@RequestParam("goodsId") Long goodsId,
									@RequestParam("amount") Integer amount,
									HttpSession session) {

		List<ReturningGoods> returningList = (List<ReturningGoods>) session.getAttribute("returningList");
		Transaction transaction = (Transaction) session.getAttribute("transaction");

		ReturningGoods returningGoods = helper.getReturningGoodsFromReturningListByGoodsId(returningList, goodsId);
		if (returningGoods != null) {

			helper.setNewAmountAndSubtotalForExistReturningGoods(returningGoods, amount);

		} else {

			helper.addReturningGoodsToReturningList(returningList, transaction, goodsId, amount);

		}

		session.setAttribute("totalReturningPrice", helper.calculateTotalReturningPrice(returningList));
		return "returns/returns-home.html";

	}

}
