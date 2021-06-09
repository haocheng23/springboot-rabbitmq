package com.haocheng.rabbitmqconsumer.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:    扇形消费者
 * @Remark:         备注
 * @Author:         haocheng
 * @Date:           2021-06-08 20:12:36
 */
@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiverC {

    @RabbitHandler
    public void process(Map testMessage){
        System.out.println("FanoutReceiverC消费者收到消息 :" + testMessage.toString());
    }
}
