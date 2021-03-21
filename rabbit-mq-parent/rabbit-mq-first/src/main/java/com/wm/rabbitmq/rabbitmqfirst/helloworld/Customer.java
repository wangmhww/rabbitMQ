package com.wm.rabbitmq.rabbitmqfirst.helloworld;

import com.rabbitmq.client.*;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wangm
 * @title: Customer
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2112:33
 */
public class Customer {
    public static void main(String[] args) throws IOException, TimeoutException {

        //  获取TCP长连接
        Connection connection = RabbitUtil.getConnection();
        // 创建通信通道
        Channel channel = connection.createChannel();
        // 创建队列
        // 第一个参数  队列名称
        // 第二个参数  是否持久化 false表示不持久化 mq关掉则数据丢失
        // 第三个参数  是否队列私有化 false代表所有用户都可以访问 true代表只有第一次拥有它的消费者才能使用
        // 第四个参数  是否自动删除 false代表停掉之后不自动删除这个队列
        // 其他参数 额外参数 null
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false,false, null);

        // 消费数据
        // 第一个参数  队列名称
        // 第二个参数  手动确认消费数据
        // 第三个参数
        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD, false, new Reciver(channel));

    }
}
class Reciver extends DefaultConsumer{

    private Channel channel;

    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body);
        System.out.println("消费者接收到的消息：" + message);

        System.out.println("消费者接收到的消息：" + envelope.getDeliveryTag());
        // 第二个参数  只确认当前的消息
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}