package com.ouyang.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ouyang.goods.Goods;
import com.ouyang.goods.GoodsRepository;
import com.ouyang.transaction.object.RequestItem;
import com.ouyang.transaction.object.ResponseItem;
import com.ouyang.transaction.object.TradeRequest;
import com.ouyang.transaction.object.TradeResponse;
import com.ouyang.transaction.object.Transaction;
import com.ouyang.transaction.object.TransactionItem;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository trasactionRepository;
	
	@Autowired
	private GoodsRepository goodsRepository;

	@Transactional(rollbackFor = Exception.class)
	public TradeResponse trade(TradeRequest tradeRequest) {

		Transaction transaction = this.saveTransactionAndTransactionItems(tradeRequest);
		return this.createTradeResponse(transaction);
		
	}
	
	private Transaction saveTransactionAndTransactionItems(TradeRequest tradeRequest) {
		
		Transaction transaction = new Transaction();
		transaction.setCustomerId(tradeRequest.getCustomerId());
		transaction.setStoreId(tradeRequest.getStoreId());
		List<TransactionItem> transactionItems = this.createTrasactionItems(transaction, tradeRequest.getItems());
		transaction.setTotalPrice(this.calculateTotalPrice(transactionItems));
		transaction.setItems(transactionItems);
		return trasactionRepository.save(transaction);
		
	}

	private List<TransactionItem> createTrasactionItems(Transaction transaction, List<RequestItem> requestItems) {

		List<TransactionItem> trasactionItems = new ArrayList<>();

		for (RequestItem requestItem : requestItems) {

			TransactionItem transactionItem = new TransactionItem();
			transactionItem.setTransaction(transaction);
			
			transactionItem.setGoodsId(requestItem.getGoodsId());
			transactionItem.setGoodsNumber(requestItem.getNumber());

			Goods goods = goodsRepository.findById(requestItem.getGoodsId()).get();
			transactionItem.setGoodsPrice(goods.getPrice());
			transactionItem.setGoodsName(goods.getName());

			trasactionItems.add(transactionItem);

		}

		return trasactionItems;

	}

	private BigDecimal calculateTotalPrice(List<TransactionItem> transactionItems) {

		return transactionItems.stream().map(TransactionItem::getGoodsPrice).reduce(BigDecimal::add).get();

	}
	
	private TradeResponse createTradeResponse(Transaction transaction) {
		
		TradeResponse tradeResponse = new TradeResponse();
		tradeResponse.setId(transaction.getId());
		tradeResponse.setTotalPrice(transaction.getTotalPrice());
		tradeResponse.setItems(this.createResponseItems(transaction.getItems()));
		
		return tradeResponse;
		
	}
	
	private List<ResponseItem> createResponseItems(List<TransactionItem> transactionItems) {
		
		List<ResponseItem> responseItems = new ArrayList<>();
		
		for (TransactionItem transactionItem : transactionItems) {

			ResponseItem responseItem = new ResponseItem();
			responseItem.setGoodsName(transactionItem.getGoodsName());
			responseItem.setNumber(transactionItem.getGoodsNumber());
			responseItem.setPrice(transactionItem.getGoodsPrice());

			responseItems.add(responseItem);

		}
		
		return responseItems;
		
	}

}
