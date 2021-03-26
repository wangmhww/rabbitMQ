package com.wm.rabbitmq.service;


import com.wm.rabbitmq.domain.Message;

/**
 * @author wangm
 * @title: Producer
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:23
 */

public interface SendService {
    public void send(Message message);
}
