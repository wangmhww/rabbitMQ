package com.wm.rabbitmq;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;

/**
 * @author wangm
 * @title: ProducerTest
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2123:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    // 测试confirm模式
    @Test
    public void testConfirm(){

        // 定义回调  当消息无法到达交换机的时候会进行回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param ask 是否成功
             * @param s 错误原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ask, String s) {
                System.out.println("接收到confirm消息");
                // 当ask为true 消息已到达交换机
                if (ask){
                    // 接收成功
                    System.out.println("接收到成功消息..." + s);
                } else {
                    // 接收失败
                    System.out.println("接收到失败消息..." + s);
                }
            }
        });

        // 将路由设置错误就会走confirm 的ask为false
        rabbitTemplate.convertAndSend("test_change_confirm","confirm", "spring....");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 测试return模式
    @Test
    public void testReturns(){

        // 设置交换机处理失败消息的模式 为true的时候,表示到达不了队列的值,会将消息重新返回给发送者
        rabbitTemplate.setMandatory(true);
        // 定义return
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("return执行了....");

                System.out.println("message:" + new String(returnedMessage.getMessage().getBody()));
                System.out.println("replyCode:" + returnedMessage.getReplyCode());
                System.out.println("replyText:" + returnedMessage.getReplyText());
                System.out.println("exchange:" + returnedMessage.getExchange());
                System.out.println("routingKey:" + returnedMessage.getRoutingKey());

            }
        });

        // 将routing key写错就到达不了队列中
        rabbitTemplate.convertAndSend("test_change_confirm","confirm", "message return....");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 批量发送消息,让消费着每次拉去指定数量的消息
    @Test
    public void testQos(){
        for (int i = 0; i < 10; i++) {
            System.out.println("发送消息");
            rabbitTemplate.convertAndSend("test_change_confirm","confirm","message confirm "+ i);
        }

    }
    @Test
    public void testTtl(){
        for (int i = 0; i < 10; i++){
            rabbitTemplate.convertAndSend("rabbitmq-exchange-ttl","ttt.hhh","message confirm "+ i);
            System.out.println("发送消息");
        }
    }

    // 死信交换机
    @Test
    public void testDlx(){
//        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.hhh","发送消息..... ");
        for (int i = 0 ; i < 20 ; i++){

            rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.hhh","发送消息..... ");
        }
        System.out.println("发送消息");
    }

    @Test
    public void testDelay(){

        try {
            rabbitTemplate.convertAndSend("order_exchange","order.hhh","发送消息..... ");

            for (int i = 10; i > 0; i--) {
                System.out.println(i + "...");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
