package com.example.JMSProvider2.service;

import com.example.JMSProvider2.listeners.CallbackListener;
import jakarta.jms.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    public void createJMSReplyToService(Session session, Integer it) throws JMSException, InterruptedException {
        //This method will send one message and wait until it receive information that message was handle. Then it send another msg etc....
        TemporaryQueue temporaryQueue = session.createTemporaryQueue();
        Destination destination = session.createQueue("MyOwnQueue");
        MessageProducer producer = session.createProducer(destination);
        //We have to create this object to have receive information handler
        MessageConsumer callbackInformation = session.createConsumer(temporaryQueue);
        callbackInformation.setMessageListener(new CallbackListener());
        TextMessage message = session.createTextMessage();
        message.setStringProperty("index","I am message "+it);
        message.setStringProperty("currency", "PLN -> EU");
        message.setFloatProperty("value", 4.20f);
        message.setJMSReplyTo(temporaryQueue);
        message.setJMSCorrelationID("jms = " + it);
        producer.send(message);
    }

    public void createBasicTextMessageScheduler(Session session, Integer it) throws JMSException {
        //This type of message will have been sent and will be waiting in queue until some consumer consume it
        Destination destination = session.createQueue("MyOwnQueue");
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage();
        //Setting the message scheduler
        message.setLongProperty("_AMQ_SCHED_DELIVERY", System.currentTimeMillis()+5000); // we delay 5 sec.
        message.setStringProperty("index","I am message "+it);
        message.setStringProperty("currency", "PLN -> EU");
        message.setFloatProperty("value", (float) (Math.random()*1000) + 1);
        producer.send(message);
        producer.close();
    }
}
