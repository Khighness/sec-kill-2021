package top.parak.service;

import top.parak.domain.OrderInfo;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀服务接口
 */
public interface SeckillService {

    public OrderInfo kill(UserVO user, GoodsVO goods);

    public long getSeckillResult(long userId, long goodsId);

}
