package com.wm.rabbitmq.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.rabbitmq.contant.RabbitContant;
import com.wm.rabbitmq.domain.User;
import com.wm.rabbitmq.service.CallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author wangm
 * @title: CallServiceImpl
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:57
 */
@Service
@Slf4j
public class CallServiceImpl implements CallService {

    private RabbitTemplate rabbitTemplate;

    public CallServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void call(User user)  {
        try {
            String uuid = UUID.randomUUID().toString();
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(user) ;
            CorrelationData correlationData = new CorrelationData(uuid);

            rabbitTemplate.convertAndSend(RabbitContant.RABBIT_EXCHANGE,RabbitContant.RABBIT_ROUTING_KEY_CALL,message.getBytes(),correlationData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
