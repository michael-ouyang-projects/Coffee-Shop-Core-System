package com.ouyang.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ouyang.goods.GoodsRepository;
import com.ouyang.store.StoreRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository trasactionRepository;

	@Autowired
	private TransactionItemRepository transactionItemRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private GoodsRepository goodsRepository;

	@Transactional(rollbackFor = Exception.class)
	public TradeResponse trade(TradeRequest tradeRequest) {

		Transaction transaction = new Transaction();
		transaction.setStoreId(tradeRequest.getStoreId());
		transaction.setCustomerId(tradeRequest.getCustomerId());
		List<TransactionItem> trasactionItems = this.getTrasactionItems(tradeRequest.getItems());
		transaction.setTotalPrice(this.getTotalPrice(trasactionItems));
		Long transactionId = trasactionRepository.save(transaction).getId();

		// TODO

		TradeResponse tradeResponse = new TradeResponse();
		return tradeResponse;

	}

	private List<TransactionItem> getTrasactionItems(List<ItemRequest> items) {

		List<TransactionItem> trasactionItems = new ArrayList<>();

		for (ItemRequest itemRequest : items) {

			TransactionItem transactionItem = new TransactionItem();
			transactionItem.setGoodsId(itemRequest.getGoodsId());
			transactionItem.setNumber(itemRequest.getNumber());
			transactionItem.setPrice(BigDecimal.ONE);

			trasactionItems.add(transactionItem);

		}

		return trasactionItems;

	}

	private BigDecimal getTotalPrice(List<TransactionItem> trasactionItems) {

		return trasactionItems.stream().map(TransactionItem::getPrice).reduce(BigDecimal::add).get();

	}

}
