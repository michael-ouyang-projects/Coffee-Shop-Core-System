# Retail-Store-Core-System

Purpose: This is a web application that deal with retail store's daily demands.

Technology: Spring Framework, Thymeleaf, Mysql, Kafka, Docker, Google Cloud Platform.

Use Cases:
  1. Trading.
  2. CRUD Customers Info, Goods Info, Branches Info and all the other business components.
  3. Create reports for managers to observe and evaluate market conditions.
  4. Real time automatic inventory management. 

-------------------------------------------------------------------------------------------

Database(Tables and Columns)
  1. Customer: id, name, sex, age
  2. Goods: id, name, price, type
  3. Branch: id, name, address
  4. Transaction: id, tradeDate, customerId, storeId, totalPrice
  5. TransactionItem: id, transactionId, goodsId, goodsName, amount, subtotal

-------------------------------------------------------------------------------------------
Docker Command (tmp)
  
  ./mvnw clean package  
  sudo docker build -t ouyang/rscs .  
  sudo docker run -d --net host --name rscs ouyang/rscs:latest  
  sudo docker logs rscs  
  sudo docker stop rscs  
  sudo docker rm rscs  
  sudo docker rmi ouyang/rscs:latest  
  
