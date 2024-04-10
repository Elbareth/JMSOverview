package com.example.JMSConsumer2.listeners;

import jakarta.jms.*;

public class JMSListener implements MessageListener {

    private Session session;

    private MessageProducer producer;

    public JMSListener(Session session, MessageProducer producer)
    {
        super();
        this.session = session;
        this.producer = producer;
    }
    @Override
    public void onMessage(Message message) {
        try {

            Destination replyTo = message.getJMSReplyTo();
            if(replyTo != null)
            {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(textMessage.getStringProperty("index"));
                TextMessage done = this.session.createTextMessage("Job finished");
                done.setJMSCorrelationID(message.getJMSCorrelationID());
                producer.send(replyTo, done);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
