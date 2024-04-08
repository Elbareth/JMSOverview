package com.example.JMSConsumer2.listeners;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class PointToPointListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String index = message.getStringProperty("index");
            String currency = message.getStringProperty("currency");
            float value = message.getFloatProperty("value");
            System.out.println("We receive following message "+index+" "+currency+" = "+value);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
