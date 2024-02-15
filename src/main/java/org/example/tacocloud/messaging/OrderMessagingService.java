package org.example.tacocloud.messaging;

import org.example.tacocloud.entity.TacoOrder;
import org.springframework.jms.core.JmsTemplate;

public interface OrderMessagingService {
    public void sendOrder(TacoOrder order);

}
