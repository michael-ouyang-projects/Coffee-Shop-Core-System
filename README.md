# Coffee-Shop-Core-System

Purpose: This is a Java Web Application that deal with Coffee Shop's daily demands.

Technology: Spring Framework, Thymeleaf, Mysql, Kafka, Docker, Google Cloud Platform.

Use Cases:
  1. Coffee Trading/Returning.
  2. Create and Update Customers' Info, Coffee's Info, Branches' Info and all the other Business Components.
  3. Create reports for managers to observe and evaluate market conditions.
  4. Real time automatic inventory management. 

-------------------------------------------------------------------------------------------

Database(Tables and Columns)
  1. Branch: id, name, address
  2. Customer: id, name, sex, age
  3. Coffee: id, name, size, price, type
  4. Transaction: id, tradeDate, customerId, branchId, totalPrice
  5. TransactionItem: id, transactionId, coffeeId, amount

-------------------------------------------------------------------------------------------
Docker Command (tmp)
  
  ./mvnw clean package  
  sudo docker build -t ouyang/coffee .  
  sudo docker run -d --net host --name coffee ouyang/coffee:latest  
  sudo docker logs coffee
  sudo docker stop coffee
  sudo docker rm coffee
  sudo docker rmi ouyang/coffee:latest  
  
