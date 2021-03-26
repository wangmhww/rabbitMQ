package com.wm.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wangm
 * @title: QosListener
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/220:23
 */
@Component
public class OrderListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            // 1 获取到的消息
            System.out.println(new String(message.getBody()));

            System.out.println("业务处理逻辑");

            System.out.println("根据订单查询其状态");

            System.out.println("判断状态是否为支付成功");

            System.out.println("取消订单，回滚库存");

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (IOException e) {
            System.out.println("出现异常状态 ，拒绝签收");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true,false);
            e.printStackTrace();
        }

    }
}
