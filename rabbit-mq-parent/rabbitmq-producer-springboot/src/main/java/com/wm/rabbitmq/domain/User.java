package com.wm.rabbitmq.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangm
 * @title: User
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:58
 */
@Data
public class User implements Serializable {
    private String userName;

    private String account;
}
