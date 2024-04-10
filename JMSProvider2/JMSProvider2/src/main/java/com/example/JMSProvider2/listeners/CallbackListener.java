package com.example.JMSProvider2.listeners;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CallbackListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String id = message.getJMSCorrelationID();
            if(id.startsWith("jms"))
            {
                System.out.println("Following message was handled "+id);
            }
            TextMessage txtResponse = (TextMessage) message;
            System.out.println(txtResponse.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
