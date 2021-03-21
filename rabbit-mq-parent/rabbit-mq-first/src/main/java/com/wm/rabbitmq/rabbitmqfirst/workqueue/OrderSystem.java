package com.wm.rabbitmq.rabbitmqfirst.workqueue;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @author wangm
 * @title: OrderSystem
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2114:39
 */
public class OrderSystem {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtil.getConnection();

        // 创建通信通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false,false, null);
        for (int i = 0; i < 100; i++) {
            SMS sms = new SMS("乘客" + i, "135093993" + i ,"您的车票购买能成功！");
            String jsonSMS = new Gson().toJson(sms);
            channel.basicPublish("",RabbitConstant.QUEUE_SMS,null, jsonSMS.getBytes());
        }

        channel.close();
        connection.close();
        System.out.println("数据发送成功");
    }
}
