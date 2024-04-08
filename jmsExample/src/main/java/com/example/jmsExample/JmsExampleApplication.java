package com.example.jmsExample;

import com.example.jmsExample.service.JMSService;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsExampleApplication implements CommandLineRunner {

	@Autowired
	private JMSService jmsService;

	public static void main(String[] args) {
		SpringApplication.run(JmsExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try
		{
			this.jmsService.sendAndReceiveMessage();
		}
		catch (JMSException ex)
		{
			ex.printStackTrace();
		}
	}
}
