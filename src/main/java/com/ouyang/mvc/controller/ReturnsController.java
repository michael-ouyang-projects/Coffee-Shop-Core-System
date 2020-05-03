package com.ouyang.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.mvc.controller.helper.ReturnsControllerHelper;
import com.ouyang.mvc.model.TradeGoods;

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

		List<TradeGoods> returningList = (List<TradeGoods>) session.getAttribute("returningList");
		
		TradeGoods returningGoods = helper.getReturningGoodsFromReturningListByGoodsId(returningList, goodsId);
		if (returningGoods != null) {

			helper.setNewAmountAndSubtotalForExistReturningGoods(returningGoods, amount, session);

		} else {

			helper.addReturningGoodsToReturningList(returningList, goodsId, amount, session);

		}

		session.setAttribute("totalReturningPrice", helper.calculateTotalReturningPrice(returningList));
		return "returns/returns-home.html";

	}

}
