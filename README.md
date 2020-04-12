# Retail-Store-Core-System
Monolithic Spring Boot Project

This is a core system that deal with daily demands for a Retail Store.

It has 5 main services.
  1. Branch Service - For CRUD Branches.
  2. Goods Service - For CRUD Goods.
  3. Customer Service - For CRUD Customers.
  4. Transaction Service - For all the Business Trades.
  5. Report Service - For Produce Reports.

--------------------------------------------------------

DB
  1. Branch: id, name, address
  2. Goods: id, name, price, type
  3. Customer: id, name, sex, age
  4. Transaction: id, tradeDate, customerId, storeId, totalPrice
  5. TransactionItem: id, transactionId, goodsId, goodsName, goodsNumber, goodsPrice

--------------------------------------------------------
Docker Command (tmp)
  
  ./mvnw clean package  
  sudo docker build -t ouyang/rscs .  
  sudo docker run -d --net host --name rscs ouyang/rscs:latest  
  sudo docker logs rscs  
  sudo docker stop rscs  
  sudo docker rm rscs  
  sudo docker rmi ouyang/rscs:latest  
  