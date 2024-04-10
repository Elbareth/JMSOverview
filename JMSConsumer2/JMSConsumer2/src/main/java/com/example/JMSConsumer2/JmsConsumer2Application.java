package com.example.JMSConsumer2;

import com.example.JMSConsumer2.service.JMSService;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsConsumer2Application implements CommandLineRunner {

	@Autowired
	private JMSService jmsService;

	public static void main(String[] args) {
		SpringApplication.run(JmsConsumer2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try
		{
			this.jmsService.receive();
		}
		catch (JMSException | InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}
}
