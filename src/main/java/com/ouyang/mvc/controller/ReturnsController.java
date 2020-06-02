package com.ouyang.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ouyang.mvc.controller.helper.ReturnsControllerHelper;
import com.ouyang.mvc.model.TradeCoffee;
import com.ouyang.mvc.model.TradeResponse;
import com.ouyang.transaction.TransactionService;

@Controller
public class ReturnsController {

    @Autowired
    private ReturnsControllerHelper helper;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/returns-home")
    public String returnsHome(@RequestParam("tradeId") Long tradeId,
            HttpSession session) {

        helper.initSessionDataForReturnsRequest(tradeId, session);
        return "returns/returns-home.html";

    }

    @SuppressWarnings("unchecked")
    @PostMapping("/trade-home/add-returning-coffee")
    public String addReturningGoods(@RequestParam("coffeeId") Long coffeeId,
            @RequestParam("amount") Integer amount,
            HttpSession session) {

        List<TradeCoffee> returningList = (List<TradeCoffee>) session.getAttribute("returningList");

        TradeCoffee returningGoods = helper.getReturningItemFromReturningListByCoffeeId(returningList, coffeeId);
        if (returningGoods != null) {

            helper.setNewAmountAndSubtotalForExistReturningItem(returningGoods, amount, session);

        } else {

            helper.addReturningItemToReturningList(returningList, coffeeId, amount, session);

        }

        session.setAttribute("totalReturningPrice", helper.calculateTotalReturningPrice(returningList));
        return "returns/returns-home.html";

    }

    @PostMapping("/returns-home/return")
    public String doReturn(HttpSession session, Model model) {

        TradeResponse tradeResponse = transactionService.doReturn(session);

        model.addAttribute("tradeResponse", tradeResponse);
        return "returns/returns-result.html";

    }

}
