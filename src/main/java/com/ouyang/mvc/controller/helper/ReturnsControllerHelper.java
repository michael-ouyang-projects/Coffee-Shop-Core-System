package com.ouyang.mvc.controller.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ouyang.customer.CustomerService;
import com.ouyang.goods.Goods;
import com.ouyang.goods.GoodsService;
import com.ouyang.mvc.model.TradeGoods;
import com.ouyang.transaction.Transaction;
import com.ouyang.transaction.TransactionItem;
import com.ouyang.transaction.TransactionService;
import com.ouyang.utils.RSCSRuntimeException;

@Component
public class ReturnsControllerHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReturnsControllerHelper.class);
	
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private GoodsService goodsService;

	
	public void initSessionDataForReturnsRequest(Long tradeId, HttpSession session) {

		Transaction transaction = transactionService.queryTrade(tradeId);

		session.setAttribute("transaction", transaction);
		session.setAttribute("customer", customerService.queryCustomerById(transaction.getCustomerId()));
		session.setAttribute("buyingList", createBuyingListFromTransactionItems(transaction.getItems()));
		session.setAttribute("returningList", new ArrayList<TradeGoods>());

	}

	private List<TradeGoods> createBuyingListFromTransactionItems(List<TransactionItem> items) {

		List<TradeGoods> buyingList = new ArrayList<>();

		for (TransactionItem item : items) {

			Goods goods = goodsService.queryGoodsById(item.getGoodsId());

			TradeGoods buyingGoods = new TradeGoods();
			buyingGoods.setGoodsId(goods.getId());
			buyingGoods.setGoodsName(goods.getName());
			buyingGoods.setAmount(item.getAmount());
			buyingGoods.setPrice(goods.getPrice());
			buyingGoods.setSubtotal(goods.getPrice().multiply(new BigDecimal(item.getAmount())));

			buyingList.add(buyingGoods);
			
		}

		return buyingList;

	}

	public TradeGoods getReturningGoodsFromReturningListByGoodsId(List<TradeGoods> returningList, Long goodsId) {

		return returningList
				.stream()
				.filter(returningGoods -> returningGoods.getGoodsId().equals(goodsId))
				.findFirst()
				.orElse(null);

	}

	public void setNewAmountAndSubtotalForExistReturningGoods(TradeGoods returningGoods, Integer amount, HttpSession session) {

		try {
		
			TradeGoods buyingGoods = getBuyingGoodsFromBuyingListByGoodsId(returningGoods.getGoodsId(), session);
			int newReturningAmount = returningGoods.getAmount() + amount;

			if(newReturningAmount <= buyingGoods.getAmount()) {
				
				returningGoods.setAmount(newReturningAmount);
				
			} else {
				
				throw new RSCSRuntimeException("returning amount larger than buying amount!");
				
			}
			
			
			returningGoods.setSubtotal(returningGoods.getPrice().multiply(new BigDecimal(newReturningAmount)));
			
		} catch (RSCSRuntimeException e) {
			
			LOGGER.info(e.getMessage());
			
		}

	}

	public void addReturningGoodsToReturningList(List<TradeGoods> returningList, Long goodsId, Integer amount, HttpSession session) {

		try {
			
			TradeGoods buyingGoods = getBuyingGoodsFromBuyingListByGoodsId(goodsId, session);
			
			TradeGoods returningGoods = new TradeGoods();
			returningGoods.setGoodsId(buyingGoods.getGoodsId());
			returningGoods.setGoodsName(buyingGoods.getGoodsName());
			
			if(amount <= buyingGoods.getAmount()) {
				
				returningGoods.setAmount(amount);
				
			} else {
				
				throw new RSCSRuntimeException("returning amount larger than buying amount!");
				
			}
			
			returningGoods.setPrice(buyingGoods.getPrice());
			returningGoods.setSubtotal(buyingGoods.getPrice().multiply(new BigDecimal(amount)));

			returningList.add(returningGoods);
			
		} catch (NoSuchElementException e) {
			
			LOGGER.info("returning goodsId not exist in buyingList!");
			
		} catch (RSCSRuntimeException e) {
			
			LOGGER.info(e.getMessage());
			
		}

	}
	
	@SuppressWarnings("unchecked")
	private TradeGoods getBuyingGoodsFromBuyingListByGoodsId(Long goodsId, HttpSession session) {
		
		List<TradeGoods> buyingList = (List<TradeGoods>) session.getAttribute("buyingList");

		return buyingList
				.stream()
				.filter(buyingGoods -> buyingGoods.getGoodsId().equals(goodsId))
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
