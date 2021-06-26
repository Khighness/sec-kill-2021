package top.parak.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.parak.util.BeanUtil;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote RabbitMQ生产者
 */
@Service
public class MQSender {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage sm) {
        String msg = BeanUtil.beanToString(sm);
        log.info("『SecKill』 send message => [{}]", msg);
        amqpTemplate.convertAndSend(MQConfigure.SEC_KILL_QUEUE, msg);
    }

    public void sendDirect(Object message) {
        String msg = BeanUtil.beanToString(message);
        log.info("『Direct』 send message => [{}]", message);
        amqpTemplate.convertAndSend(MQConfigure.DIRECT_QUEUE, msg);
    }

    public void sendFanout(Object message) {
        String msg = BeanUtil.beanToString(message);
        log.info("『Fanout』 send message => [{}]", message);
        amqpTemplate.convertAndSend(MQConfigure.FANOUT_EXCHANGE, MQConfigure.DEFAULT_ROUTING_KEY, msg);
    }

    public void sendTopic(Object message) {
        String msg = BeanUtil.beanToString(message);
        log.info("『Topic』 send message => [{}]", message);
        amqpTemplate.convertAndSend(MQConfigure.TOPIC_EXCHANGE, MQConfigure.TOPIC_ROUTING_KEY1, msg);
        amqpTemplate.convertAndSend(MQConfigure.TOPIC_EXCHANGE, MQConfigure.TOPIC_ROUTING_KEY2, msg);
    }

    public void sendHeader(Object message) {
        String msg = BeanUtil.beanToString(message);
        log.info("『Header』 send message => [{}]", message);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("header1", "value1");
        properties.setHeader("header2", "value2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfigure.HEADER_EXCHANGE, MQConfigure.DEFAULT_ROUTING_KEY, obj);
    }

}
