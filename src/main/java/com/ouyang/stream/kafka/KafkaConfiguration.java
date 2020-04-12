package com.ouyang.stream.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfiguration {

	@Value(value = "${kafka.servers}")
    private String kafkaServers;
	
	
	@Bean
	public KafkaAdmin admin() {
		
	    Map<String, Object> configs = new HashMap<>();
	    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
	    return new KafkaAdmin(configs);
	    
	}

	@Bean
	public NewTopic report() {
		
	    return new NewTopic("report", 1, (short) 1);
	    
	}
	
}
