package com.wm.rabbitmq.controller;

import com.wm.rabbitmq.domain.Message;
import com.wm.rabbitmq.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangm
 * @title: ProducerController
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:19
 */
@RestController
@RequestMapping("/api/v1/send")
@Slf4j
public class SendController {

    private SendService sendService;

    public SendController(SendService sendService) {
        this.sendService = sendService;
    }

    @PostMapping("/message")
    public void send(@RequestBody Message message){
        sendService.send(message);
    }
}
