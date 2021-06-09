package com.haocheng.rabbitmqconsumer.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:    直联消费者
 * @Remark:         备注
 * @Author:         haocheng
 * @Date:           2021-06-08 17:16:47
 */
@Component
@RabbitListener(queues = "TestDirectQueue")
public class DirectReceiver2 {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("第二个-DirectReceiver消费者收到消息：" + testMessage.toString());
    }

}
