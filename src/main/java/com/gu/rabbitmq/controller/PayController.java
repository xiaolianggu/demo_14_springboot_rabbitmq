package com.gu.rabbitmq.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayController {
	public static Boolean isCanPay = true;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;

	@RequestMapping("/toPay")
	public String toPay() throws UnsupportedEncodingException {
		isCanPay = true;
		MessageProperties properties = new MessageProperties();
		properties.setExpiration("10000");
		Message message = new Message("发送了延时消息".getBytes("utf-8"), properties);
		rabbitTemplate.convertAndSend("ex.pay.waiting", "pay.waiting", message);
		System.out.println("发送了延时消息当前时间为:"+System.currentTimeMillis());
		return "toPay";
	}

	
	@RequestMapping("/toOrder")
	public String toOrder() throws UnsupportedEncodingException {
		return "toOrder";
	}

	@RequestMapping("/pay")
	public String pay(Model model) throws UnsupportedEncodingException {
		if(!isCanPay) {
			return "fail";
		}
		return "success";
	}
	
	
}
