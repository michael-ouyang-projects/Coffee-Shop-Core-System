# Retail-Store-Core-System
Monolithic Spring Boot Project

Like the title, this is a core system that deal with all the demand for a retail store.

It has 5 main services.
  1. Store Service - For CRUD Stores.
  2. Goods Service - For CRUD Goods.
  3. Customer Service - For CRUD Customers.
  4. Transaction Service - For all the Trades.
  5. Report Service - For Produce Reports.

-----------------------------------------------

DB
  1. Store: id, branchName, address
  2. Goods: id, name, price, type
  3. Customer: id, name, sex, age
  4. Transaction: id, customerId, storeId, totalPrice
  5. TransactionItem: id, transactionId, goodsId, number, price
