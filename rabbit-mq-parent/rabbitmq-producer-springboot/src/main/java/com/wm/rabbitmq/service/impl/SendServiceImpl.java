package com.wm.rabbitmq.service.impl;

import com.wm.rabbitmq.contant.RabbitContant;
import com.wm.rabbitmq.domain.Message;
import com.wm.rabbitmq.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wangm
 * @title: ProducerServiceImpl
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:24
 */
@Service
@Slf4j
public class SendServiceImpl implements SendService {

    private RabbitTemplate rabbitTemplate;

    public SendServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Message message) {
        log.info("发送消息");
        rabbitTemplate.convertAndSend(RabbitContant.RABBIT_EXCHANGE, RabbitContant.RABBIT_ROUTING_KEY_SEND, message);

    }
}
