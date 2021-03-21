package com.wm.rabbitmq.rabbitmqfirst.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wangm
 * @title: RabbitUtil
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2114:09
 */
public class RabbitUtil {
    private static ConnectionFactory cnf = new ConnectionFactory();

    static {
        cnf.setHost("47.110.71.90");
        cnf.setPort(5672);
        cnf.setUsername("wangming");
        cnf.setPassword("ming123456");
        cnf.setVirtualHost("rabbitmq-1");
    }

    public static Connection getConnection(){
        Connection connection = null;

        try {
            connection = cnf.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
