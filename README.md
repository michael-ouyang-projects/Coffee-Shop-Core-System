# Retail-Store-Core-System

Purpose: This is a web application that deal with retail store's daily demands.

Technology: Spring Framework, Thymeleaf, Mysql, Kafka, Docker, Google Cloud Platform.

Use Cases:
  1. Trading.
  2. Create and Update Customers Info, Goods Info, Branches Info and all the other business components.
  3. Create reports for managers to observe and evaluate market conditions.
  4. Real time automatic inventory management. 

-------------------------------------------------------------------------------------------

Database(Tables and Columns)
  1. Branch: id, name, address
  2. Customer: id, name, sex, age
  3. Goods: id, name, price, type
  4. Item: id, goodsId, branchId
  5. Transaction: id, tradeDate, customerId, branchId, totalPrice
  6. TransactionDetail: transactionId, itemId

-------------------------------------------------------------------------------------------
Docker Command (tmp)
  
  ./mvnw clean package  
  sudo docker build -t ouyang/rscs .  
  sudo docker run -d --net host --name rscs ouyang/rscs:latest  
  sudo docker logs rscs  
  sudo docker stop rscs  
  sudo docker rm rscs  
  sudo docker rmi ouyang/rscs:latest  
  
