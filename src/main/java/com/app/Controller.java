package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class Controller {
	private static final String TOPIC = "myTopic";
	private KafkaTemplate<String, Person> kafkaTemplate;

	@Autowired
	public Controller(KafkaTemplate<String, Person> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping
	public void post(@RequestBody Person person) {
		kafkaTemplate.send(Controller.TOPIC, person);
	}

	@KafkaListener(topics = Controller.TOPIC)
	public void getFromKafka(Person person) {
		System.out.println(person);
	}
}
