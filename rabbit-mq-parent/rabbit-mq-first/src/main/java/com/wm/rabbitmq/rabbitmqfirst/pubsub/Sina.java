package com.wm.rabbitmq.rabbitmqfirst.pubsub;

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
public class Sina {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitUtil.getConnection();

            final Channel channel = connection.createChannel();
            //
            channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);
            // 队列交换机绑定 第一个参数时队列  第二个参数时交换机,第三个参数时routing key
            channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER, "");
            // 每次只取一个
            channel.basicQos(1);

            channel.basicConsume(RabbitConstant.QUEUE_SINA, false, new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("新浪天气信息：" + new String(body));
                    // 第二个参数  只确认当前的消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
