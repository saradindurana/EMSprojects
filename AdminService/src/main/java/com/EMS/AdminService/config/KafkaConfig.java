package com.EMS.AdminService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfig {

	
	public NewTopic topic() {
		return TopicBuilder.name("course").build();
	}
	
}
