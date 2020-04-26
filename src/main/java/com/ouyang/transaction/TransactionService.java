package com.ouyang.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ouyang.goods.Goods;
import com.ouyang.goods.GoodsRepository;
import com.ouyang.mvc.model.TradeGoods;
import com.ouyang.mvc.model.TradeRequest;
import com.ouyang.mvc.model.TradeResponse;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository trasactionRepository;

	@Autowired
	private GoodsRepository goodsRepository;
	
	
	@Transactional(rollbackFor = Exception.class)
	public TradeResponse trade(TradeRequest tradeRequest) {

		Transaction transaction = this.saveTransactionAndBuyingGoods(tradeRequest);
		return this.createTradeResponse(transaction);

	}

	private Transaction saveTransactionAndBuyingGoods(TradeRequest tradeRequest) {

		Transaction transaction = new Transaction();
		transaction.setTradeDate(new Date());
		transaction.setCustomerId(tradeRequest.getCustomerId());
		transaction.setBranchId(tradeRequest.getBranchId());
		transaction.setTotalPrice(tradeRequest.getTotalPrice());
		transaction.setItems(this.createTrasactionItems(transaction, tradeRequest.getBuyingList()));
		return trasactionRepository.save(transaction);

	}

	private List<TransactionItem> createTrasactionItems(Transaction transaction, List<TradeGoods> buyingList) {

		List<TransactionItem> trasactionItems = new ArrayList<>();

		for (TradeGoods buyingGoods : buyingList) {

			TransactionItem transactionItem = new TransactionItem();
			transactionItem.setTransaction(transaction);
			transactionItem.setGoodsId(buyingGoods.getGoodsId());
			transactionItem.setAmount(buyingGoods.getAmount());

			trasactionItems.add(transactionItem);

		}

		return trasactionItems;

	}

	private TradeResponse createTradeResponse(Transaction transaction) {

		TradeResponse tradeResponse = new TradeResponse();
		tradeResponse.setTradeId(transaction.getId());
		tradeResponse.setTradeDate(transaction.getTradeDate());
		tradeResponse.setTotalPrice(transaction.getTotalPrice());
		tradeResponse.setBuyingList(this.createBuyingGoodsListAfterSave(transaction.getItems()));

		return tradeResponse;

	}

	private List<TradeGoods> createBuyingGoodsListAfterSave(List<TransactionItem> transactionItems) {

		List<TradeGoods> buyingGoodsList = new ArrayList<>();

		for (TransactionItem transactionItem : transactionItems) {

			Goods goods = goodsRepository.findById(transactionItem.getGoodsId()).get();
			
			TradeGoods buyingGoods = new TradeGoods();
			buyingGoods.setGoodsId(transactionItem.getGoodsId());
			buyingGoods.setGoodsName(goods.getName());
			buyingGoods.setAmount(transactionItem.getAmount());
			buyingGoods.setPrice(goods.getPrice());
			buyingGoods.setSubtotal(goods.getPrice().multiply(new BigDecimal(transactionItem.getAmount())));

			buyingGoodsList.add(buyingGoods);

		}

		return buyingGoodsList;

	}

	public Transaction queryTrade(Long tradeId) {

		return trasactionRepository.findById(tradeId).get();

	}

}
