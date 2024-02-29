package com.EMS.studentService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
@Configuration
public class KafkaConfig {
	@KafkaListener(topics="course",groupId = "group-1")
	public void courseUpdate(String value) {
		System.out.println(value);
	}

}
