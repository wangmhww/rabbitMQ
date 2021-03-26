package com.wm.rabbitmq.callBack;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author wangm
 * @title: MsgSendReturnCallBack
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2623:36
 */

public class MsgSendReturnCallBack implements RabbitTemplate.ReturnsCallback {

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("消息已送达broker但未送到queue");
    }
}
