package com.ouyang.mvc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.branch.Branch;
import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.goods.Goods;
import com.ouyang.goods.GoodsService;
import com.ouyang.mvc.model.BuyingGoods;
import com.ouyang.transaction.TransactionService;
import com.ouyang.transaction.object.TradeRequest;
import com.ouyang.transaction.object.TradeResponse;

@Controller
public class TradeController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private TransactionService transactionService;

	@PostMapping("/trade-home")
	public String tradeHome(@RequestParam("customerId") Long customerId, 
							HttpSession session) {
		
		session.setAttribute("customer", customerService.queryCustomerById(customerId));
		session.setAttribute("buyingGoodsList", new ArrayList<>());
		session.setAttribute("totalPrice", BigDecimal.ZERO);
		return "trade/trade-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/add-buying-goods")
	public String addBuyingGoods(@RequestParam("goodsId") Long goodsId,
							 	 @RequestParam("number") Integer number,
							 	 HttpSession session) {

		List<BuyingGoods> buyingGoodsList = (List<BuyingGoods>) session.getAttribute("buyingGoodsList");
		
		BuyingGoods buyingGoods = null;
		if((buyingGoods = getBuyingGoodsIfExist(buyingGoodsList, goodsId)) != null) {

			int newNumber = buyingGoods.getNumber() + number;
			buyingGoods.setNumber(newNumber);
			buyingGoods.setSubtotal(buyingGoods.getPrice().multiply(new BigDecimal(newNumber)));
			
		} else {
			
			Goods goods = goodsService.queryGoodsById(goodsId);
			buyingGoods = new BuyingGoods();
			buyingGoods.setGoodsId(goodsId);
			buyingGoods.setGoodsName(goods.getName());
			buyingGoods.setNumber(number);
			buyingGoods.setPrice(goods.getPrice());
			buyingGoods.setSubtotal(goods.getPrice().multiply(new BigDecimal(number)));
			buyingGoodsList.add(buyingGoods);
			
		}
		
		session.setAttribute("totalPrice", calculateTotalPrice(buyingGoodsList));
		return "trade/trade-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/trade")
	public String trade(HttpSession session,
						Model model) {
		
		TradeRequest tradeRequest = new TradeRequest();
		tradeRequest.setCustomerId(((Customer) session.getAttribute("customer")).getId());
		tradeRequest.setBranchId(((Branch) session.getAttribute("branch")).getId());
		tradeRequest.setBuyingGoodsList((List<BuyingGoods>) session.getAttribute("buyingGoodsList"));
		tradeRequest.setTotalPrice((BigDecimal) session.getAttribute("totalPrice"));
		TradeResponse tradeResponse = transactionService.trade(tradeRequest);
		
		model.addAttribute("tradeResponse", tradeResponse);
		return "trade/trade-result.html";
		
	}
	
	private BuyingGoods getBuyingGoodsIfExist(List<BuyingGoods> buyingGoodsList, Long goodsId) {
		
		return buyingGoodsList
				.stream()
				.filter(buyingGoods -> buyingGoods.getGoodsId().equals(goodsId))
				.findFirst()
				.orElse(null);

	}
	
	private BigDecimal calculateTotalPrice(List<BuyingGoods> buyingGoodsList) {
		
		return buyingGoodsList
				.stream()
				.map(BuyingGoods::getSubtotal)
				.reduce(BigDecimal::add)
				.get();
		
	}

}
