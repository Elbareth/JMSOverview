package com.example.JMSProvider2.service;

import jakarta.jms.*;
import org.springframework.stereotype.Component;

@Component
public class MessageTypeService {

    public void createBasicTextMessage(Session session, Integer it) throws JMSException {
        //This type of message will have been sent and will be waiting in queue until some consumer consume it
        Destination destination = session.createQueue("MyOwnQueue");
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage();
        message.setStringProperty("index","I am message "+it);
        message.setStringProperty("currency", "PLN -> EU");
        message.setFloatProperty("value", (float) (Math.random()*1000) + 1);
        producer.send(message);
        producer.close();
    }

    public void createMassageWithTopic(Session session, Integer it) throws JMSException {
        //This type of message will have topic and will have been sent to all consumers that listening about the topic
        Destination destination = session.createTopic("CurrencyValue");
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage();
        message.setStringProperty("index","I am message "+it);
        message.setStringProperty("currency", "PLN -> EU");
        message.setFloatProperty("value", 4.20f);
        producer.send(message);
        producer.close();
    }
}
