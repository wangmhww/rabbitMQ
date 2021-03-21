package com.wm.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author wangm
 * @title: AckListener
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2123:52
 */
@Component
public class AckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 1 获取消息的id
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 2 获取消息信息
        System.out.println("message:" + new String(message.getBody()));

        // 3 进行业务处理
        System.out.println("==========进行业务处理=========");
        try {
            int i = 5/0;
            // 4 进行消息手动签收
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            // 拒绝签收
            /**
             *  第三个参数:requeue:重回队列,如果设置为true,则消息重新返回queue,broker会重新发送该消息给消费端
             */
            channel.basicNack(deliveryTag, true, false);
        }

    }
}
