package com.wm.rabbitmq.service;

import com.wm.rabbitmq.domain.User;

/**
 * @author wangm
 * @title: CallService
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:57
 */
public interface CallService {

    public void call(User user);
}
