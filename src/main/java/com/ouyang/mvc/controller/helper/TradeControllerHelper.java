package com.ouyang.mvc.controller.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ouyang.branch.Branch;
import com.ouyang.coffee.Coffee;
import com.ouyang.coffee.CoffeeService;
import com.ouyang.customer.Customer;
import com.ouyang.customer.CustomerService;
import com.ouyang.mvc.model.TradeCoffee;
import com.ouyang.mvc.model.TradeRequest;

@Component
public class TradeControllerHelper {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CoffeeService goodsService;

    public void initSessionDataForNewTrade(HttpSession session, Long customerId) {

        session.setAttribute("customer", customerService.queryCustomerById(customerId));
        session.setAttribute("buyingList", new ArrayList<>());

    }

    public TradeCoffee getBuyingItemFromBuyingList(List<TradeCoffee> buyingList, Long coffeeId) {

        return buyingList
                .stream()
                .filter(buyingGoods -> buyingGoods.getCoffeeId().equals(coffeeId))
                .findFirst()
                .orElse(null);

    }

    public void setNewAmountAndSubtotalForExistBuyingGoods(TradeCoffee buyingGoods, Integer amount) {

        int newBuyingAmount = buyingGoods.getAmount() + amount;
        buyingGoods.setAmount(newBuyingAmount);
        buyingGoods.setSubtotal(buyingGoods.getPrice().multiply(new BigDecimal(newBuyingAmount)));

    }

    public void addBuyingItemToBuyingList(List<TradeCoffee> buyingList, Long coffeeId, Integer amount) {

        Coffee goods = goodsService.queryCoffeeById(coffeeId);

        TradeCoffee buyingGoods = new TradeCoffee();
        buyingGoods.setCoffeeId(goods.getId());
        buyingGoods.setCoffeeName(goods.getName());
        buyingGoods.setAmount(amount);
        buyingGoods.setPrice(goods.getPrice());
        buyingGoods.setSubtotal(goods.getPrice().multiply(new BigDecimal(amount)));

        buyingList.add(buyingGoods);

    }

    public BigDecimal calculateTotalPrice(List<TradeCoffee> buyingGoodsList) {

        return buyingGoodsList
                .stream()
                .map(TradeCoffee::getSubtotal)
                .reduce(BigDecimal::add)
                .get();

    }

    @SuppressWarnings("unchecked")
    public TradeRequest createTradeRequest(HttpSession session) {

        TradeRequest tradeRequest = new TradeRequest();
        tradeRequest.setCustomerId(((Customer) session.getAttribute("customer")).getId());
        tradeRequest.setBranchId(((Branch) session.getAttribute("branch")).getId());
        tradeRequest.setTradeList((List<TradeCoffee>) session.getAttribute("buyingList"));
        tradeRequest.setTotalPrice((BigDecimal) session.getAttribute("totalPrice"));

        return tradeRequest;

    }

}
