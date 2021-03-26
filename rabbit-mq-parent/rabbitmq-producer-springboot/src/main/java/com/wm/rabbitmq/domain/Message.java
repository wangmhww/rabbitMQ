package com.wm.rabbitmq.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wangm
 * @title: Message
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:53
 */
@Data
public class Message implements Serializable {
    private Map<String, String> header;

    private Map<String, String> body;

}
