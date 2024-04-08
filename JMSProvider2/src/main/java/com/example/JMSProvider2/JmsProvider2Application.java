package com.example.JMSProvider2;

import com.example.JMSProvider2.service.JMSService;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsProvider2Application implements CommandLineRunner {

	@Autowired
	private JMSService jmsService;

	public static void main(String[] args) {
		SpringApplication.run(JmsProvider2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try
		{
			for(int i = 0; i<100; i++)
			{
				this.jmsService.sendMessage(i);
			}
		}
		catch(JMSException ex)
		{
			ex.printStackTrace();
		}
	}
}
