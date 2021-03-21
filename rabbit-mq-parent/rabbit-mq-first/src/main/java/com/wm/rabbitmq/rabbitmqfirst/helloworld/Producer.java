package com.wm.rabbitmq.rabbitmqfirst.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wangm
 * @title: Producer
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/212:01
 */
public class Producer {
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

        String message = "wangming";
        // 发送数据
        // 第一个参数  出表示交换机 暂时用不到
        // 第二个参数  队列名称
        // 第三个参数  额外参数 null
        // 第四个参数  发送的消息
        channel.basicPublish("", RabbitConstant.QUEUE_HELLOWORLD, null, message.getBytes());
        channel.close();
        connection.close();

        System.out.println("消息发送成功");
    }

}
