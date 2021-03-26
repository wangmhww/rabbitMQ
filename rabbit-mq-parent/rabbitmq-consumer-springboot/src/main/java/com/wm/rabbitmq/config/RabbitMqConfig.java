package com.wm.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangm
 * @title: RabbitMqConfig
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/270:22
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public DirectRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        DirectRabbitListenerContainerFactory  factory = new DirectRabbitListenerContainerFactory ();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(2);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
