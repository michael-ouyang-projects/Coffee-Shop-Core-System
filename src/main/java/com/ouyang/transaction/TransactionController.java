package com.ouyang.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ouyang.transaction.object.TradeRequest;
import com.ouyang.transaction.object.TradeResponse;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transaction")
	public TradeResponse trade(@RequestBody TradeRequest tradeRequest) {

		return transactionService.trade(tradeRequest);

	}

}
