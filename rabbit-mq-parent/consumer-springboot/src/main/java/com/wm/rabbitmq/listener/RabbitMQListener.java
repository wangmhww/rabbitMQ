package com.wm.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author wangm
 * @title: RabbitMQListener
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2122:27
 */
@Component
public class RabbitMQListener {

    // 定义方法监听器 RabbitListener的参数用于表示监听的是哪一个队列
    @RabbitListener(queues = "boot_queue")
    public void ListenerQueue(Message message) {
        System.out.println("message :" + new String(message.getBody()));
    }
}
