package com.ouyang.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.mvc.controller.helper.TradeControllerHelper;
import com.ouyang.mvc.model.TradeCoffee;
import com.ouyang.mvc.model.TradeRequest;
import com.ouyang.mvc.model.TradeResponse;
import com.ouyang.transaction.TransactionService;

@Controller
public class TradeController {

    @Autowired
    private TradeControllerHelper helper;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/trade-home")
    public String tradeHome(@RequestParam("customerId") Long customerId,
            HttpSession session) {

        helper.initSessionDataForNewTrade(session, customerId);
        return "trade/trade-home.html";

    }

    @SuppressWarnings("unchecked")
    @PostMapping("/trade-home/add-buying-coffee")
    public String addBuyingGoods(@RequestParam("coffeeId") Long coffeeId,
            @RequestParam("amount") Integer amount,
            HttpSession session) {

        List<TradeCoffee> buyingList = (List<TradeCoffee>) session.getAttribute("buyingList");

        TradeCoffee buyingGoods = helper.getBuyingItemFromBuyingList(buyingList, coffeeId);
        if (buyingGoods != null) {

            helper.setNewAmountAndSubtotalForExistBuyingGoods(buyingGoods, amount);

        } else {

            helper.addBuyingItemToBuyingList(buyingList, coffeeId, amount);

        }

        session.setAttribute("totalPrice", helper.calculateTotalPrice(buyingList));
        return "trade/trade-home.html";

    }

    @PostMapping("/trade-home/trade")
    public String trade(HttpSession session,
            Model model) {

        TradeRequest tradeRequest = helper.createTradeRequest(session);
        TradeResponse tradeResponse = transactionService.trade(tradeRequest);

        model.addAttribute("tradeResponse", tradeResponse);
        return "trade/trade-result.html";

    }

}
