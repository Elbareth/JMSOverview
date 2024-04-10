package com.example.JMSProvider2.service;

import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JMSService {

    private String tcpAddress = "tcp://localhost:61616";
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    @Autowired
    private MessageTypeService messageTypeService;

    private void before() throws JMSException {
        this.connectionFactory = new ActiveMQConnectionFactory(this.tcpAddress);
        this.connection = connectionFactory.createConnection();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private void run(Integer it) throws JMSException, InterruptedException {
        //messageTypeService.createBasicTextMessage(session, it);
        //messageTypeService.createMassageWithTopic(session,it);
        //messageTypeService.createJMSReplyToService(session, it);
        messageTypeService.createBasicTextMessageScheduler(session, it);
    }

private void after() throws JMSException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public void sendMessage(Integer id) throws JMSException, InterruptedException {
        before();
        run(id);
        after();
    }
}
