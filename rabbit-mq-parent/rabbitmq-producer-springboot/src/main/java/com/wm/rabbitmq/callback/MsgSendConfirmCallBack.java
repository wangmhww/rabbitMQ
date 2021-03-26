package com.wm.rabbitmq.callback;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author wangm
 * @title: MsgSendConfirmCallBack
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2623:32
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ask, String s) {
        if (ask) {
            System.out.println("消息broker确认接收.....");
        } else {
            System.out.println("borker接收消息失败.....");
        }
    }
}
