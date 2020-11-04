package com.gu.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.gu.rabbitmq.controller.PayController;


@Component
public class MyMessageListener {

    @RabbitListener(queues = "q.pay.ttl-waiting")
    public void whenMessageCome(@Payload String messageStr) {
        
        System.out.println("消息过期当前时间为:"+System.currentTimeMillis());
        PayController.isCanPay = false;
    }

}
