package com.haocheng.rabbitmqprovider.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    private Map<String, Object> createParamMap(String messageId, String messageData) {
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messageId", messageId);
        paramMap.put("messageData", messageData);
        paramMap.put("createTime", createTime);
        return paramMap;
    }

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        Map<String, Object> paramMap = createParamMap(messageId, messageData);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", paramMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        Map<String, Object> paramMap = createParamMap(messageId, messageData);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", paramMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        Map<String, Object> paramMap = createParamMap(messageId, messageData);
        Map<String, Object> womanMap;
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", paramMap);
        return "ok";
    }


    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        Map<String, Object> paramMap = createParamMap(messageId, messageData);
        rabbitTemplate.convertAndSend("fanoutExchange", null, paramMap);
        return "ok";
    }

    /**
     * 1、消息推送到server，但是在server里找不到交换机
     * @author     haocheng
     * @date       2021-06-08 20:53:08
     * @return     触发ConfirmCallback回调函数
     */
    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        Map<String, Object> paramMap = createParamMap(messageId, messageData);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", paramMap);
        return "ok";
    }

    /**
     * 消息推送到server，找到交换机了，但是没找到队列
     * @author     haocheng
     * @date       2021-06-08 20:54:57
     * @return     触发的是 ConfirmCallback和RetrunCallback两个回调函数
     */
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        Map<String, Object> paramMap = createParamMap(messageId, messageData);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", paramMap);
        return "ok";
    }
}
