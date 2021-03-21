package com.wm.rabbitmq.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangm
 * @title: Test
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/220:08
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-rabbitmq-consumer.xml");
    }
}
