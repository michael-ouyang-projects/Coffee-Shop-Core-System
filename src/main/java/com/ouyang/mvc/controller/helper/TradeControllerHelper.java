package com.ouyang.mvc.controller.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ouyang.branch.Branch;
import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.goods.Goods;
import com.ouyang.goods.GoodsService;
import com.ouyang.mvc.model.BuyingGoods;
import com.ouyang.transaction.object.TradeRequest;

@Component
public class TradeControllerHelper {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GoodsService goodsService;
	
	
	public void initSessionDataForNewTrade(HttpSession session, Long customerId) {
		
		session.setAttribute("customer", customerService.queryCustomerById(customerId));
		session.setAttribute("buyingList", new ArrayList<>());
		session.setAttribute("totalPrice", BigDecimal.ZERO);
		
	}
	
	public BuyingGoods getBuyingGoodsFromBuyingList(List<BuyingGoods> buyingList, Long goodsId) {
		
		return buyingList
				.stream()
				.filter(buyingGoods -> buyingGoods.getId().equals(goodsId))
				.findFirst()
				.orElse(null);

	}
	
	public void setNewAmountAndSubtotalForExistBuyingGoods(BuyingGoods buyingGoods, Integer amount) {
		
		int newBuyingAmount = buyingGoods.getAmount() + amount;
		buyingGoods.setAmount(newBuyingAmount);
		buyingGoods.setSubtotal(buyingGoods.getPrice().multiply(new BigDecimal(newBuyingAmount)));
		
	}
	
	public void addBuyingGoodsToBuyingList(List<BuyingGoods> buyingList, Long goodsId, Integer amount) {
		
		Goods goods = goodsService.queryGoodsById(goodsId);
		
		BuyingGoods buyingGoods = new BuyingGoods();
		buyingGoods.setId(goodsId);
		buyingGoods.setName(goods.getName());
		buyingGoods.setAmount(amount);
		buyingGoods.setPrice(goods.getPrice());
		buyingGoods.setSubtotal(goods.getPrice().multiply(new BigDecimal(amount)));
		
		buyingList.add(buyingGoods);
		
	}
	
	public BigDecimal calculateTotalPrice(List<BuyingGoods> buyingGoodsList) {
		
		return buyingGoodsList
				.stream()
				.map(BuyingGoods::getSubtotal)
				.reduce(BigDecimal::add)
				.get();
		
	}
	
	@SuppressWarnings("unchecked")
	public TradeRequest createTradeRequest(HttpSession session) {
		
		TradeRequest tradeRequest = new TradeRequest();
		tradeRequest.setCustomerId(((Customer) session.getAttribute("customer")).getId());
		tradeRequest.setBranchId(((Branch) session.getAttribute("branch")).getId());
		tradeRequest.setBuyingList((List<BuyingGoods>) session.getAttribute("buyingList"));
		tradeRequest.setTotalPrice((BigDecimal) session.getAttribute("totalPrice"));
		
		return tradeRequest;
		
	}
	
}