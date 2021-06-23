package top.parak.service;

import org.apache.ibatis.annotations.Param;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.vo.GoodsVO;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 订单服务接口
 */
public interface OrderService {

    public SecKillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId);

    public OrderInfo createOrder(User user, GoodsVO goods);

}
