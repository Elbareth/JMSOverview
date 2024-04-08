package com.example.jmsExample.service;


import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class JMSService {
    private final String connectionUri = "tcp://localhost:61616";
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    private void before() throws JMSException {
        this.connectionFactory = new
                ActiveMQConnectionFactory(connectionUri);
        this.connection = connectionFactory.createConnection();
        this.connection.start();
        this.session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);
        this.destination = session.createQueue("artemis-server-0.0.0.0");
    }

    private void run() throws JMSException {
        //we send the message
        MessageProducer producer =  this.session.createProducer(destination);
        TextMessage message = this.session.createTextMessage();
        //we set information we want to send
        message.setText("Wiadomość do przekazania");
        producer.send(message);
        producer.close();
        //now we can receive the information
        MessageConsumer consumer = session.createConsumer(destination);
        message = (TextMessage) consumer.receive();
        System.out.println(message);
    }

    private void after() throws JMSException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public void sendAndReceiveMessage() throws JMSException {
        before();
        run();
        after();
    }
}
