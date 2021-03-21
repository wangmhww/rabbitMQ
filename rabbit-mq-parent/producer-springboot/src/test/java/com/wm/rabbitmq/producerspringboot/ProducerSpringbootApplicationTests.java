package com.wm.rabbitmq.producerspringboot;

import com.wm.rabbitmq.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProducerSpringbootApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void send() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ask, String s) {
                System.out.println("Confirm方法被执行.....");
                if (ask){
                    System.out.println("接收成功消息" + s );
                } else {
                    System.out.println("接收失败消息" + s);
                }
            }
        });

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,
                "boot.haha",
                "汪明fff11");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
