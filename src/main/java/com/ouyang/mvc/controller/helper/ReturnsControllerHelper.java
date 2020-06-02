package com.ouyang.mvc.controller.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
import com.ouyang.transaction.Transaction;
import com.ouyang.transaction.TransactionItem;
import com.ouyang.transaction.TransactionService;
import com.ouyang.utils.CoffeeRuntimeException;

@Component
public class ReturnsControllerHelper {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CoffeeService goodsService;

    public void initSessionDataForReturnsRequest(Long tradeId, HttpSession session) {

        Transaction transaction = transactionService.queryTrade(tradeId);

        session.setAttribute("transaction", transaction);
        session.setAttribute("customer", customerService.queryCustomerById(transaction.getCustomerId()));
        session.setAttribute("buyingList", createBuyingListFromTransactionItems(transaction.getItems()));
        session.setAttribute("returningList", new ArrayList<TradeCoffee>());

    }

    private List<TradeCoffee> createBuyingListFromTransactionItems(List<TransactionItem> items) {

        List<TradeCoffee> buyingList = new ArrayList<>();

        for (TransactionItem item : items) {

            Coffee coffee = goodsService.queryCoffeeById(item.getCoffeeId());

            TradeCoffee buyingGoods = new TradeCoffee();
            buyingGoods.setCoffeeId(coffee.getId());
            buyingGoods.setCoffeeName(coffee.getName());
            buyingGoods.setAmount(item.getAmount());
            buyingGoods.setPrice(coffee.getPrice());
            buyingGoods.setSubtotal(coffee.getPrice().multiply(new BigDecimal(item.getAmount())));

            buyingList.add(buyingGoods);

        }

        return buyingList;

    }

    public TradeCoffee getReturningItemFromReturningListByCoffeeId(List<TradeCoffee> returningList, Long goodsId) {

        return returningList
                .stream()
                .filter(returningGoods -> returningGoods.getCoffeeId().equals(goodsId))
                .findFirst()
                .orElse(null);

    }

    public void setNewAmountAndSubtotalForExistReturningItem(TradeCoffee returningGoods, Integer amount, HttpSession session) {

        TradeCoffee buyingGoods = getBuyingItemFromBuyingListByCoffeeId(returningGoods.getCoffeeId(), session);
        int newReturningAmount = returningGoods.getAmount() + amount;

        if (newReturningAmount <= buyingGoods.getAmount()) {

            returningGoods.setAmount(newReturningAmount);

        } else {

            throw new CoffeeRuntimeException("returning amount larger than buying amount!");

        }

        returningGoods.setSubtotal(returningGoods.getPrice().multiply(new BigDecimal(newReturningAmount)));

    }

    public void addReturningItemToReturningList(List<TradeCoffee> returningList, Long goodsId, Integer amount, HttpSession session) {

        TradeCoffee buyingGoods = getBuyingItemFromBuyingListByCoffeeId(goodsId, session);

        TradeCoffee returningGoods = new TradeCoffee();
        returningGoods.setCoffeeId(buyingGoods.getCoffeeId());
        returningGoods.setCoffeeName(buyingGoods.getCoffeeName());

        if (amount <= buyingGoods.getAmount()) {

            returningGoods.setAmount(amount);

        } else {

            throw new CoffeeRuntimeException("returning amount larger than buying amount!");

        }

        returningGoods.setPrice(buyingGoods.getPrice());
        returningGoods.setSubtotal(buyingGoods.getPrice().multiply(new BigDecimal(amount)));

        returningList.add(returningGoods);

    }

    @SuppressWarnings("unchecked")
    public TradeRequest createReturnsRequest(HttpSession session) {

        TradeRequest retrunsRequest = new TradeRequest();
        retrunsRequest.setCustomerId(((Customer) session.getAttribute("customer")).getId());
        retrunsRequest.setBranchId(((Branch) session.getAttribute("branch")).getId());
        retrunsRequest.setTotalPrice((BigDecimal) session.getAttribute("totalReturningPrice"));
        retrunsRequest.setTradeList((List<TradeCoffee>) session.getAttribute("returningList"));

        return retrunsRequest;

    }

    @SuppressWarnings("unchecked")
    private TradeCoffee getBuyingItemFromBuyingListByCoffeeId(Long coffeeId, HttpSession session) {

        List<TradeCoffee> buyingList = (List<TradeCoffee>) session.getAttribute("buyingList");

        try {

            return buyingList
                    .stream()
                    .filter(buyingGoods -> buyingGoods.getCoffeeId().equals(coffeeId))
                    .findFirst()
                    .get();

        } catch (NoSuchElementException e) {

            throw new NoSuchElementException("returning coffee not exist in buyingList!");

        }

    }

    public BigDecimal calculateTotalReturningPrice(List<TradeCoffee> returningList) {

        return returningList
                .stream()
                .map(TradeCoffee::getSubtotal)
                .reduce(BigDecimal::add)
                .get();

    }

}
