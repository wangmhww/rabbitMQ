package com.wm.rabbitmq.rabbitmqfirst.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitConstant;
import com.wm.rabbitmq.rabbitmqfirst.utils.RabbitUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 发布者
 * @author wangm
 * @title: WeatherBureau
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2115:28
 */
public class WeatherBureau {
    public static void main(String[] args) throws IOException {
        try {
            Connection connection = RabbitUtil.getConnection();

            String input = new Scanner(System.in).next();

            Channel channel = connection.createChannel();
            // 第一个参数为交换机
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER, "",null, input.getBytes());

            channel.close();
            connection.close();

        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
