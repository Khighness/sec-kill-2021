package top.parak.rabbit;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.parak.service.GoodsService;
import top.parak.service.SeckillService;
import top.parak.util.BeanUtil;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote RabbitMQ消费者
 */
@Service
public class MQReceiver {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues = MQConfigure.SEC_KILL_QUEUE)
    public void receiveSeckillMessage(String message) {
        SeckillMessage sm = BeanUtil.stringToBean(message, SeckillMessage.class);
        log.info("『SecKill』 receive message => [{}]", message);
        if (ObjectUtils.isEmpty(sm))
            throw new NullPointerException("SecKill message must be not null");

        UserVO userVO = sm.getUserVO();
        Long goodsId = sm.getGoodsId();
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        Integer stockCount = goodsVO.getStockCount();
        if (stockCount <= 0) {
            return;
        }
        seckillService.kill(userVO, goodsVO);
    }

    @RabbitListener(queues = MQConfigure.DIRECT_QUEUE)
    public void receiveDirect(String message) {
        log.info("『Direct』 receive message => [{}]", message);
    }

    @RabbitListener(queues = MQConfigure.FANOUT_QUEUE1)
    public void receiveFanout1(String message) {
        log.info("『Fanout』 receive queue1 message => [{}]", message);
    }

    @RabbitListener(queues = MQConfigure.FANOUT_QUEUE2)
    public void receiveFanout2(String message) {
        log.info("『Fanout』 receive queue2 message => [{}]", message);
    }

    @RabbitListener(queues = MQConfigure.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info("『Topic』 receive queue1 message => [{}]", message);
    }

    @RabbitListener(queues = MQConfigure.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info("『Topic』 receive queue2 message => [{}]", message);
    }

    @RabbitListener(queues = MQConfigure.HEADER_QUEUE)
    public void receiveHeader(String message) {
        log.info("『Header』 receive message => [{}]", message);
    }

}
