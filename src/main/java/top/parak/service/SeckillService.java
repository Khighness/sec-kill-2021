package top.parak.service;

import top.parak.domain.OrderInfo;
import top.parak.domain.User;
import top.parak.vo.GoodsVO;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀服务接口
 */
public interface SeckillService {

    public OrderInfo kill(User user, GoodsVO goods);

}
