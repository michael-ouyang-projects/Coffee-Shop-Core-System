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
							HttpSession session, 
							Model model) {
		
		Customer customer = customerService.queryCustomerById(customerId);
		session.setAttribute("customer", customer);
		session.setAttribute("buyingGoodsList", new ArrayList<>());
		
		model.addAttribute("customer", customer);
		return "trade-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/add-buying-goods")
	public String addBuyingGoods(@RequestParam("goodsId") Long goodsId,
							 	 @RequestParam("number") Integer number,
							 	 HttpSession session,
							 	 Model model) {

		List<BuyingGoods> buyingGoodsList = (List<BuyingGoods>) session.getAttribute("buyingGoodsList");
		
		BuyingGoods buyingGoods = null;
		if((buyingGoods = getBuyingGoodsIfExist(buyingGoodsList, goodsId)) != null) {
			
			BigDecimal goodsPrice = buyingGoods.getPrice().divide(new BigDecimal(buyingGoods.getNumber()));
			buyingGoods.setNumber(buyingGoods.getNumber() + number);
			buyingGoods.setPrice(goodsPrice.multiply(new BigDecimal(buyingGoods.getNumber())));
			
		} else {
			
			Goods goods = goodsService.queryGoodsById(goodsId);
			buyingGoods = new BuyingGoods();
			buyingGoods.setGoodsId(goodsId);
			buyingGoods.setGoodsName(goods.getName());
			buyingGoods.setNumber(number);
			buyingGoods.setPrice(goods.getPrice().multiply(new BigDecimal(number)));
			buyingGoodsList.add(buyingGoods);
			
		}
		
		BigDecimal totalPrice = buyingGoodsList.stream().map(BuyingGoods::getPrice).reduce(BigDecimal::add).get();
		
		model.addAttribute("customer", session.getAttribute("customer"));
		model.addAttribute("buyingGoodsList", buyingGoodsList);
		model.addAttribute("totalPrice", totalPrice);
		return "trade-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/trade")
	public String trade(@RequestParam("totalPrice") String totalPrice, 
						HttpSession session,
						Model model) {
		
		TradeRequest tradeRequest = new TradeRequest();
		tradeRequest.setCustomerId(((Customer)session.getAttribute("customer")).getId());
		tradeRequest.setBranchId(((Branch)session.getAttribute("branch")).getId());
		tradeRequest.setBuyingGoodsList((List<BuyingGoods>) session.getAttribute("buyingGoodsList"));
		tradeRequest.setTotalPrice(new BigDecimal(totalPrice));
		TradeResponse tradeResponse = transactionService.trade(tradeRequest);
		
		model.addAttribute("tradeResponse", tradeResponse);
		return "trade-result.html";
		
	}
	
	private BuyingGoods getBuyingGoodsIfExist(List<BuyingGoods> buyingGoodsList, Long goodsId) {
		
		return buyingGoodsList
			   .stream()
			   .filter(buyingGoods -> buyingGoods.getGoodsId().equals(goodsId))
	           .findFirst()
	           .orElse(null);

	}

}
