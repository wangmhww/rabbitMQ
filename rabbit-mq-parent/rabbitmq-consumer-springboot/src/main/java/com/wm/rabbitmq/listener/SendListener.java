package com.wm.rabbitmq.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wm.rabbitmq.contant.RabbitMqContant;
import com.wm.rabbitmq.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author wangm
 * @title: CallListener
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2623:06
 */
@Component
@Slf4j
@RabbitListener(queues = RabbitMqContant.RABBIT_QUEUE_SEND)
public class SendListener {
    @RabbitHandler
    public void process(Message message, @Headers Map<String, Object> headers, Channel channel){
        try {
            ObjectMapper mapper=new ObjectMapper();
            String messaged=new String(message.getBody());
            User user = mapper.readValue(messaged.getBytes("utf-8"),User.class);
            log.info("接收到呼叫消息，DeliveryTag={}, account={}, name={}",message.getMessageProperties().getDeliveryTag(), user.getAccount(),user.getUserName());
            Thread.sleep(2000);
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
//    @RabbitListener(queues = RabbitMqContant.RABBIT_QUEUE_SEND)
//    public void call(Message message, @Headers Map<String, Object> headers, Channel channel){
//        try {
//            ObjectMapper mapper=new ObjectMapper();
//            String messaged=new String(message.getBody());
//            User user = mapper.readValue(messaged.getBytes("utf-8"),User.class);
//            log.info("接收到呼叫消息，DeliveryTag={}, account={}, name={}",message.getMessageProperties().getDeliveryTag(), user.getAccount(),user.getUserName());
//            Thread.sleep(2000);
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}


