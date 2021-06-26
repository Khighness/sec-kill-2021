package top.parak.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.rabbit.MQSender;
import top.parak.rabbit.SeckillMessage;
import top.parak.redis.GoodsKey;
import top.parak.redis.RedisService;
import top.parak.response.CodeMessage;
import top.parak.response.ServerResponse;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.service.SeckillService;
import top.parak.service.UserService;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀控制器
 */
@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;

    /**
     * 标记商品是否购完
     */
    private final Map<Long, Boolean> localGoodsOverMap = new HashMap<>();

    /**
     * 将商品库存预先放到redis中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        if (!ObjectUtils.isEmpty(goodsVOList)) {
            for (GoodsVO goodsVO : goodsVOList) {
                redisService.set(GoodsKey.getSeckillGoodsStock, "" + goodsVO.getId(), goodsVO.getStockCount());
                localGoodsOverMap.put(goodsVO.getId(), false);
            }
        }
    }

    /**
     * 购买
     * @param user     当前用户
     * @param goodsId  商品ID
     * @return 检验成功则返回等待状态
     */
    @PostMapping("/buy")
    @ResponseBody
    public ServerResponse buy(User user, @RequestParam("goodsId") long goodsId) {
        // 是否购完
        Boolean over = localGoodsOverMap.get(goodsId);
        if (over) {
            return new ServerResponse<>(CodeMessage.SEC_KILL_OVER);
        }
        // 预减库存
        Long stock = redisService.decrement(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if (stock <= 0) {
            localGoodsOverMap.put(goodsId, true);
            return new ServerResponse<>(CodeMessage.SEC_KILL_FAILED);
        }
        // 检验重复
        SecKillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (!ObjectUtils.isEmpty(order)) {
            return new ServerResponse<>(CodeMessage.REPEATE_CLICK);
        }
        // 进入队列
        UserVO userVO = new UserVO(user.getId(), user.getMobile(), user.getNickname(),
                user.getAvatar(), user.getRegisterDate(), user.getLastLoginDate(), user.getLoginCount());
        SeckillMessage sm = new SeckillMessage(userVO, goodsId);
        mqSender.sendSeckillMessage(sm);
        return ServerResponse.success("Waiting");
    }

    /**
     * 秒杀结果
     * <li>-1 => 秒杀失败
     * <li>0  => 排队中
     * <li>orderId => 秒杀成功
     * @param user    当前用户
     * @param goodsId 商品ID
     * @return 秒杀状态
     */
    @GetMapping("/res")
    @ResponseBody
    public ServerResponse<Long> res(User user, @RequestParam("goodsId") long goodsId) {
        long res = seckillService.getSeckillResult(user.getId(), goodsId);
        return ServerResponse.success(res);
    }

}
