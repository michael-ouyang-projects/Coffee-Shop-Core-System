package com.ouyang.stream.kafka;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
	
	
	@KafkaListener(topics = "report", groupId = "reportGroup")
    public void consume(String message) throws IOException {
		
        logger.info(String.format("#### -> Consumed message -> %s", message));
        
    }
	
}
