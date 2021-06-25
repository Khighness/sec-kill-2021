package top.parak.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import top.parak.domain.OrderInfo;
import top.parak.domain.User;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.service.SeckillService;
import top.parak.vo.GoodsVO;

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

    /**
     * <p>1. 减库存
     * <p>2. 下订单
     */
    @Transactional
    @Override
    public OrderInfo kill(User user, GoodsVO goods) {
        goodsService.reduceStockCountById(goods.getId(), 1);
        return orderService.createOrder(user, goods);
    }
}
