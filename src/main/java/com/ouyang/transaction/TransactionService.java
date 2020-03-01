package com.ouyang.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository trasactionRepository;

	@Transactional(rollbackFor = Exception.class)
	public TradeResponse trade(TradeRequest tradeRequest) {

		Transaction transaction = new Transaction();
		transaction.setStoreId(tradeRequest.getStoreId());
		transaction.setCustomerId(tradeRequest.getCustomerId());
		List<TransactionItem> trasactionItems = this.getTrasactionItems(transaction, tradeRequest.getItems());
		transaction.setTotalPrice(this.getTotalPrice(trasactionItems));
		transaction.setItems(trasactionItems);
		transaction = trasactionRepository.save(transaction);

		TradeResponse tradeResponse = new TradeResponse();
		tradeResponse.setId(transaction.getId());
		tradeResponse.setTotalPrice(transaction.getTotalPrice());
		tradeResponse.setItems(this.getItemResponseList(transaction.getItems()));
		
		return tradeResponse;

	}

	private List<TransactionItem> getTrasactionItems(Transaction transaction, List<ItemRequest> itemRequestList) {

		List<TransactionItem> trasactionItems = new ArrayList<>();

		for (ItemRequest itemRequest : itemRequestList) {

			TransactionItem transactionItem = new TransactionItem();
			transactionItem.setTransaction(transaction);
			transactionItem.setGoodsId(itemRequest.getGoodsId());
			transactionItem.setNumber(itemRequest.getNumber());
			transactionItem.setPrice(BigDecimal.ONE);

			trasactionItems.add(transactionItem);

		}

		return trasactionItems;

	}

	private BigDecimal getTotalPrice(List<TransactionItem> transactionItems) {

		return transactionItems.stream().map(TransactionItem::getPrice).reduce(BigDecimal::add).get();

	}
	
	private List<ItemResponse> getItemResponseList(List<TransactionItem> transactionItems) {
		
		List<ItemResponse> itemResponseList = new ArrayList<>();
		
		for (TransactionItem transactionItem : transactionItems) {

			ItemResponse itemResponse = new ItemResponse();
			itemResponse.setGoodsName("test name");
			itemResponse.setNumber(transactionItem.getNumber());
			itemResponse.setPrice(transactionItem.getPrice());

			itemResponseList.add(itemResponse);

		}
		
		return itemResponseList;
		
	}

}
