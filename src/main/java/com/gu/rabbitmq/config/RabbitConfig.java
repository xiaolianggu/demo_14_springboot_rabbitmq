package com.gu.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	@Bean
	public Queue queueTTLWaiting() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("x-dead-letter-exchange", "ex.pay.ttlwaiting");
		props.put("x-dead-letter-routing-key", "pay.ttl-waiting");
		Queue queue = new Queue("q.pay.ttl-waiting", false, false, false, props);
		return queue;
	}

	@Bean
	public Queue queueWaiting() {
		Queue queue = new Queue("q.pay.waiting", false, false, false);
		return queue;

	}

	@Bean
	public Exchange exchangeTTLWaiting() {
		DirectExchange exchange = new DirectExchange("ex.pay.ttlwaiting", false, false);
		return exchange;
	}

	@Bean
	public Exchange exchangeWaiting() {
		DirectExchange exchange = new DirectExchange("ex.pay.waiting", false, false);
		return exchange;
	}

	@Bean
	public Binding bindingTTLWaiting() {
		return BindingBuilder.bind(queueTTLWaiting()).to(exchangeTTLWaiting()).with("pay.ttl-waiting").noargs();
	}

	@Bean
	public Binding bindingWaiting() {
		return BindingBuilder.bind(queueWaiting()).to(exchangeWaiting()).with("pay.waiting").noargs();
	}
}
