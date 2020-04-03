package com.ouyang.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ouyang.mvc.model.BuyingGoods;
import com.ouyang.stream.kafka.KafkaService;
import com.ouyang.transaction.object.TradeRequest;
import com.ouyang.transaction.object.TradeResponse;
import com.ouyang.transaction.object.Transaction;
import com.ouyang.transaction.object.TransactionItem;

@Service
public class TransactionService {

	@Autowired
	private KafkaService kafkaService;
	
	@Autowired
	private TransactionRepository trasactionRepository;

	
	@Transactional(rollbackFor = Exception.class)
	public TradeResponse trade(TradeRequest tradeRequest) {

		Transaction transaction = this.saveTransactionAndBuyingGoods(tradeRequest);
		this.sendTransactionalDataToReportService(transaction);
		return this.createTradeResponse(transaction);

	}

	private Transaction saveTransactionAndBuyingGoods(TradeRequest tradeRequest) {

		Transaction transaction = new Transaction();
		transaction.setTradeDate(new Date());
		transaction.setCustomerId(tradeRequest.getCustomerId());
		transaction.setStoreId(tradeRequest.getBranchId());
		transaction.setTotalPrice(tradeRequest.getTotalPrice());
		transaction.setItems(this.createTrasactionItems(transaction, tradeRequest.getBuyingList()));
		return trasactionRepository.save(transaction);

	}

	private List<TransactionItem> createTrasactionItems(Transaction transaction, List<BuyingGoods> buyingList) {

		List<TransactionItem> trasactionItems = new ArrayList<>();

		for (BuyingGoods buyingGoods : buyingList) {

			TransactionItem transactionItem = new TransactionItem();
			transactionItem.setTransaction(transaction);
			transactionItem.setGoodsId(buyingGoods.getId());
			transactionItem.setName(buyingGoods.getName());
			transactionItem.setAmount(buyingGoods.getAmount());
			transactionItem.setSubtotal(buyingGoods.getSubtotal());

			trasactionItems.add(transactionItem);

		}

		return trasactionItems;

	}
	
	private void sendTransactionalDataToReportService(Transaction transaction) {
		
		kafkaService.sendTransaction(transaction);
		
	}

	private TradeResponse createTradeResponse(Transaction transaction) {

		TradeResponse tradeResponse = new TradeResponse();
		tradeResponse.setId(transaction.getId());
		tradeResponse.setTradeDate(transaction.getTradeDate());
		tradeResponse.setTotalPrice(transaction.getTotalPrice());
		tradeResponse.setBuyingList(this.createBuyingGoodsListAfterSave(transaction.getItems()));

		return tradeResponse;

	}

	private List<BuyingGoods> createBuyingGoodsListAfterSave(List<TransactionItem> transactionItems) {

		List<BuyingGoods> buyingGoodsList = new ArrayList<>();

		for (TransactionItem transactionItem : transactionItems) {

			BuyingGoods buyingGoods = new BuyingGoods();
			buyingGoods.setId(transactionItem.getGoodsId());
			buyingGoods.setName(transactionItem.getName());
			buyingGoods.setAmount(transactionItem.getAmount());
			buyingGoods.setPrice(transactionItem.getSubtotal().divide(new BigDecimal(transactionItem.getAmount())));
			buyingGoods.setSubtotal(transactionItem.getSubtotal());

			buyingGoodsList.add(buyingGoods);

		}

		return buyingGoodsList;

	}

	public Transaction queryTrade(Long tradeId) {

		return trasactionRepository.findById(tradeId).get();

	}

}
