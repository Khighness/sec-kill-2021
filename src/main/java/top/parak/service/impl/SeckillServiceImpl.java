package top.parak.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.redis.RedisService;
import top.parak.redis.SeckillKey;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.service.SeckillService;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀服务实现
 */
@Service
@EnableTransactionManagement
public class SeckillServiceImpl implements SeckillService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;

    /**
     * <p>1. 减库存
     * <p>2. 下订单
     */
    @Transactional
    @Override
    public OrderInfo kill(UserVO user, GoodsVO goods) {
        boolean success = goodsService.reduceStockCountById(goods.getId(), 1);
        if (success) {
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    @Override
    public long getSeckillResult(long userId, long goodsId) {
        SecKillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
        if (!ObjectUtils.isEmpty(order)) {
            return order.getOrderId();
        } else {
            boolean over = getGoodsOver(goodsId);
            if (over) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * 设置商品秒杀完成
     * @param goodsId 商品ID
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
    }

    /**
     * 获取商品秒杀状态
     * @param goodsId 商品ID
     * @return true 已结束，false 未结束
     */
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }


}
