package com.example.JMSConsumer2.service;

import com.example.JMSConsumer2.listeners.JMSListener;
import com.example.JMSConsumer2.listeners.PointToPointListener;
import jakarta.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JMSService {

    //Information about fetching - it means buffor for our consumers, how many messages can be buffored into
    //one consumer. If we provide to large value, one consumer could have too much work and another one
    //can wait without never really working
    private final String tcpAddressWithFetching = "tcp://localhost:61616?jms.prefetchPolicy.queuePrefetch=1";
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    private void before() throws JMSException {
        this.connectionFactory = new ActiveMQConnectionFactory(this.tcpAddressWithFetching);
        this.connection = connectionFactory.createConnection();
        //this.connection.setClientID("123456789"); // only in case we want to have durable subscription
        connection.start();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //this.destination = session.createQueue("MyOwnQueue");
        //When consumer is offline it won't receive message and the message won't wait for it after it became online
        //this.destination = session.createTopic("CurrencyValue"); //in that case we wait until we receive msg with the topic
        //If we want message to wait until out consumer become online we have to create durable subscribe
        //We also need to set unique id of the program
    }

    private void run() throws JMSException, InterruptedException {
        MessageConsumer consumer = this.session.createConsumer(destination);
        //To send message when work is done we have to create anonymous producer. Anonymous producer can send message anywhere
        //MessageProducer producer = this.session.createProducer(null);
        //MessageConsumer consumer = this.session.createSharedDurableConsumer((Topic) destination, "CurrencyValue");
        //String selector = "value > 500";
        //MessageConsumer consumer = this.session.createConsumer(destination, selector); // When we want to filter our message
        consumer.setMessageListener(new PointToPointListener());
        //consumer.setMessageListener(new JMSListener(this.session, producer));
        TimeUnit.MINUTES.sleep(5);
        connection.stop();
        consumer.close();
    }

    private void after() throws JMSException {
        if (this.connection != null) {
            this.connection.close();
        }
    }
    public void receive() throws JMSException, InterruptedException {
        before();
        run();
        after();
    }
}
