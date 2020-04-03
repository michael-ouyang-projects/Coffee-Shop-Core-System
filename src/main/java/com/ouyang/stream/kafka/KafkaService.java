package com.ouyang.stream.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouyang.transaction.object.Transaction;

@Service
public class KafkaService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	public void sendTransaction(Transaction transaction) {
		
		try {
			
			String  message = objectMapper.writeValueAsString(transaction);
			logger.debug(String.format("#### -> Producing message -> %s", message));
	        this.kafkaTemplate.send("report", message);
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
			
		}
        
    }
	
}
