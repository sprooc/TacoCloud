package org.example.tacocloud.messaging;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import org.example.tacocloud.entity.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessagingService implements OrderMessagingService{
    private final JmsTemplate jmsTemplate;
    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    @Override
    public void sendOrder(TacoOrder order) {
        jmsTemplate.convertAndSend("stringsss");
        jmsTemplate.convertAndSend("afefa");

    }
}
