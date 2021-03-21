package com.wm.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author wangm
 * @title: QosListener
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/220:23
 */
@Component
public class QosListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 1 获取到的消息
        System.out.println(new String (message.getBody()));

        // 2 处理业务逻辑
        Thread.sleep(1000);
        // 进行消息签收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

    }
}
