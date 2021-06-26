package top.parak.service;

import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 订单服务接口
 */
public interface OrderService {

    public SecKillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId);

    public OrderInfo createOrder(UserVO user, GoodsVO goods);

    public OrderInfo getOrderById(long orderId);

}
