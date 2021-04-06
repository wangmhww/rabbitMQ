package com.wm.rabbitmq.config;

import com.wm.rabbitmq.callback.MsgSendConfirmCallBack;
import com.wm.rabbitmq.callback.MsgSendReturnCallBack;
import com.wm.rabbitmq.contant.RabbitContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangm
 * @title: RabbitMqConfig
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:32
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Autowired
    private ConnectionFactory connectionFactory  ;

    @Bean("exchange")
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(RabbitContant.RABBIT_EXCHANGE).durable(false).build();
    }

    // 死信交换机
    @Bean("ttlExchange")
    public Exchange ttlExchange(){
        return ExchangeBuilder.topicExchange(RabbitContant.RABBIT_TTL_EXCHANGE).durable(false).build();
    }

    // 正常队列
    @Bean("callQueue")
    public Queue callQueue(){
        return QueueBuilder.durable(RabbitContant.RABBIT_QUEUE_CALL).ttl(10000)
                .deadLetterExchange(RabbitContant.RABBIT_TTL_EXCHANGE)
                .deadLetterRoutingKey("send.#")
                .maxLength(10).build();
    }

    // 死信队列
    @Bean("sendQueue")
    public Queue sendQueue(){
        return QueueBuilder.durable(RabbitContant.RABBIT_QUEUE_SEND).build();
    }

    // 正常队列与交换机绑定
    @Bean
    public Binding callBinding(@Qualifier("exchange") Exchange exchange,@Qualifier("callQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("call.#").noargs();
    }

    // 死信队列与交换机绑定
    @Bean
    public Binding sendlBinding(@Qualifier("ttlExchange") Exchange exchange,@Qualifier("sendQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("send.#").noargs();
    }

    /**
     * 自定义rabbit template用于数据的接收和发送
     * 可以设置消息确认机制和回调
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // template.setMessageConverter(); 可以自定义消息转换器  默认使用的JDK的，所以消息对象需要实现Serializable

        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());

        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        template.setReturnsCallback(msgSendReturnCallBack());
        // 开启return模式
        template.setMandatory(true);
        return template;
    }

    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

    @Bean
    public MsgSendReturnCallBack msgSendReturnCallBack(){
        return new MsgSendReturnCallBack();
    }

}
