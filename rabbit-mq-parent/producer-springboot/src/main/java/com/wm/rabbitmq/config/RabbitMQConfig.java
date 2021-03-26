package com.wm.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @author wangm
 * @title: RabbitMQConfig
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2121:58
 */
//@Configuration
public class RabbitMQConfig {

    // 交换机名称
    public static final String EXCHANGE_NAME = "boot_topic_exchange";

    // 定义队列名称
    public static final String QUEUE_NAME = "boot_queue";

    // 1.声明交换机
    @Bean("bootExchange")
    public Exchange bootExchange() {
        // durable是否持久化
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }


    // 2.声明队列
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 3.队列与交换机绑定
    @Bean
    public Binding bindQueueExchange (@Qualifier("bootQueue") Queue queue,@Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
