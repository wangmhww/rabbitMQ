package com.wm.rabbitmq.controller;

import com.wm.rabbitmq.domain.User;
import com.wm.rabbitmq.service.CallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangm
 * @title: CallController
 * @projectName rabbit-mq-parent
 * @description: TODO
 * @date 2021/3/2622:51
 */
@RestController
@RequestMapping("/api/v1/call")
@Slf4j
public class CallController {

    private CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping("/user")
    public void call(@RequestBody User user){
        callService.call(user);
    }
}
