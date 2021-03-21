package com.wm.rabbitmq.rabbitmqfirst.workqueue;

import com.rabbitmq.client.*;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;

/**
 * @author wangm
 * @title: SMSSender
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2114:49
 */
public class SMSSender2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtil.getConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false,false,false, null);
        // 如果不写basicQos（1）则MQ会将所有请求平均发送给消费者
        // basicQos MQ不再会对消费真一次发送多个请求，而是消费者处理完一个消息后（确认后），在队列中获取下一个
        channel.basicQos(1); //处理完一个取一个

        channel.basicConsume(RabbitConstant.QUEUE_SMS, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    String jsonSMS = new String(body);
                    System.out.println("SMSSender2-发送短信成功：" + jsonSMS);
                    Thread.sleep(50);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
