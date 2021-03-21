package com.wm.rabbitmq.rabbitmqfirst.comfirm;

import com.rabbitmq.client.*;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;

/**
 * 消费者
 * @author wangm
 * @title: Baidu
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2115:33
 */
public class Baidu {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitUtil.getConnection();

            final Channel channel = connection.createChannel();
            //
            channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null);
            // 队列交换机绑定
            channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "us.#");

            // 每次只取一个
            channel.basicQos(1);

            channel.basicConsume(RabbitConstant.QUEUE_BAIDU, false,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("百度收到的天气消息:" + new String(body));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
