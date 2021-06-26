package top.parak.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote RabbitMQ配置
 */
@Configuration
public class MQConfigure {
    public static final String DEFAULT_ROUTING_KEY = "";
    public static final String SEC_KILL_QUEUE = "parak.queue.seckill";

    @Bean
    public Queue seckillQueue() {
        return new Queue(SEC_KILL_QUEUE, true);
    }

    public static final String DIRECT_QUEUE = "parak.queue.direct";

    public static final String FANOUT_EXCHANGE = "parak.exchange.fanout";
    public static final String FANOUT_QUEUE1 = "parak.queue.fanout1";
    public static final String FANOUT_QUEUE2 = "parak.queue.fanout2";

    public static final String TOPIC_EXCHANGE = "parak.exchange.topic";
    public static final String TOPIC_QUEUE1 = "parak.queue.topic1";
    public static final String TOPIC_QUEUE2 = "parak.queue.topic2";
    public static final String TOPIC_ROUTING_KEY1 = "info";
    public static final String TOPIC_ROUTING_KEY2 = "warn";

    public static final String HEADER_EXCHANGE = "parak.exchange.header";
    public static final String HEADER_QUEUE = "parak.queue.header";

    /**
     * (1) Direct交换机
     * 默认交换机，声明队列即可
     * 点到点
     */
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE, true);
    }

    /**
     * (2) Fanout交换机
     * 广播式
     */
    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1, true);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2, true);
    }

    /**
     * (3) Topic交换机
     * 路由式
     */
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(TOPIC_ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_ROUTING_KEY2);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    /**
     * (4) Header交换机
     */
    @Bean
    public Binding headerBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headerQueue()).to(headersExchange()).whereAny(map).match();
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADER_EXCHANGE);
    }

    @Bean
    public Queue headerQueue() {
        return new Queue(HEADER_QUEUE, true);
    }

}
