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

import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.goods.Goods;
import com.ouyang.goods.GoodsService;
import com.ouyang.mvc.model.BuyingGood;

@Controller
public class TradeController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GoodsService goodsService;

	@PostMapping("/trade-home")
	public String branchHome(@RequestParam("customerId") Long customerId, 
							 HttpSession session, 
							 Model model) {
		
		Customer customer = customerService.queryCustomerById(customerId);
		session.setAttribute("customer", customer);
		session.setAttribute("buyingGoods", new ArrayList<>());
		
		model.addAttribute("customer", customer);
		return "trade-home.html";

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/trade-home/add-buying-goods")
	public String branchHome(@RequestParam("goodsId") Long goodsId,
							 @RequestParam("number") Integer number,
							 HttpSession session,
							 Model model) {

		List<BuyingGood> buyingGoods = (List<BuyingGood>) session.getAttribute("buyingGoods");
		
		Goods goods = goodsService.queryGoodsById(goodsId);
		BuyingGood buyingGood = new BuyingGood();
		buyingGood.setGoodsId(goodsId);
		buyingGood.setGoodsName(goods.getName());
		buyingGood.setNumber(number);
		buyingGood.setPrice(goods.getPrice().multiply(new BigDecimal(number)));
		buyingGoods.add(buyingGood);
		
		BigDecimal totalPrice = buyingGoods.stream().map(BuyingGood::getPrice).reduce(BigDecimal::add).get();
		System.out.println(totalPrice);
		
		model.addAttribute("customer", session.getAttribute("customer"));
		model.addAttribute("buyingGoods", buyingGoods);
		model.addAttribute("totalPrice", totalPrice);
		return "trade-home.html";

	}
	
	@PostMapping("/trade")
	public String addBuyingGoods() {
		
		return null;
		
	}

}
