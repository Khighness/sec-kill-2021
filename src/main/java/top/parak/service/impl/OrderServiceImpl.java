package top.parak.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import top.parak.dao.OrderDao;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.service.OrderService;
import top.parak.vo.GoodsVO;

import java.util.Date;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 订单服务实现
 */
@Service
@EnableTransactionManagement
public class OrderServiceImpl implements OrderService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao orderDao;

    @Override
    public SecKillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return orderDao.getSeckillOrderByUserIdAndGoodsId(userId, goodsId);
    }

    /**
     * <p>1. 生成订单
     * <p>2. 生成秒杀订单
     */
    @Transactional
    @Override
    public OrderInfo createOrder(User user, GoodsVO goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setDeliveryAddrId(1L);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel((short) 1);
        orderInfo.setStatus((short) 0);
        orderInfo.setCreateDate(new Date());
        long orderId = orderDao.insertOrderAndGetId(orderInfo);
        orderInfo.setId(orderId);
        SecKillOrder secKillOrder = new SecKillOrder();
        secKillOrder.setUserId(user.getId());
        secKillOrder.setOrderId(orderId);
        secKillOrder.setGoodsId(goods.getId());
        orderDao.insertSeckillOrder(secKillOrder);
        return orderInfo;
    }

}
