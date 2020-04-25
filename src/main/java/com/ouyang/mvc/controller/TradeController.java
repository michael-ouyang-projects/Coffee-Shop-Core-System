package com.ouyang.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.mvc.controller.helper.TradeControllerHelper;
import com.ouyang.mvc.model.TradeGoods;
import com.ouyang.mvc.model.TradeRequest;
import com.ouyang.mvc.model.TradeResponse;
import com.ouyang.transaction.TransactionService;

@Controller
public class TradeController {

	@Autowired
	private TradeControllerHelper helper;
	
	@Autowired
	private TransactionService transactionService;

	
	@PostMapping("/trade-home")
	public String tradeHome(@RequestParam("customerId") Long customerId, 
							HttpSession session) {
		
		helper.initSessionDataForNewTrade(session, customerId);
		return "trade/trade-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/add-buying-goods")
	public String addBuyingGoods(@RequestParam("goodsId") Long goodsId,
							 	 @RequestParam("amount") Integer amount,
							 	 HttpSession session) {

		List<TradeGoods> buyingList = (List<TradeGoods>) session.getAttribute("buyingList");
		
		TradeGoods buyingGoods = helper.getBuyingGoodsFromBuyingList(buyingList, goodsId);
		if(buyingGoods != null) {

			helper.setNewAmountAndSubtotalForExistBuyingGoods(buyingGoods, amount);
			
		} else {
			
			helper.addBuyingGoodsToBuyingList(buyingList, goodsId, amount);
			
		}
		
		session.setAttribute("totalPrice", helper.calculateTotalPrice(buyingList));
		return "trade/trade-home.html";

	}
	
	@PostMapping("/trade-home/trade")
	public String trade(HttpSession session,
						Model model) {
		
		TradeRequest tradeRequest = helper.createTradeRequest(session);
		TradeResponse tradeResponse = transactionService.trade(tradeRequest);
		
		model.addAttribute("tradeResponse", tradeResponse);
		return "trade/trade-result.html";
		
	}

}
