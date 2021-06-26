package top.parak.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import top.parak.domain.SecKillOrder;
import top.parak.redis.RedisService;
import top.parak.redis.SeckillKey;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.service.SeckillService;
import top.parak.util.MD5Util;
import top.parak.util.VerifyUtil;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

import java.awt.image.BufferedImage;
import java.util.UUID;

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
     * 购买
     * <p>1. 减库存
     * <p>2. 下订单
     * @param user  用户VI
     * @param goods 商品VO
     */
    @Transactional
    @Override
    public void kill(UserVO user, GoodsVO goods) {
        boolean success = goodsService.reduceStockCountById(goods.getId(), 1);
        if (success) {
            orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
        }
    }

    /**
     * 获取秒杀结果
     * <li>goodsId => 秒杀成功
     * <li>-1 => 秒杀失败
     * <li>0 => 继续等待
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 秒杀状态
     */
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
    @Override
    public void setGoodsOver(long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, "" + goodsId, true);
    }

    /**
     * 获取商品秒杀状态
     * @param goodsId 商品ID
     * @return true 已结束，false 未结束
     */
    @Override
    public boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }

    /**
     * 创建验证码图片
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 验证码图片
     */
    @Override
    public BufferedImage createVerifyCode(long userId, long goodsId) {
        String expression = VerifyUtil.generateExpression();
        int verifyCode = VerifyUtil.calculate(expression);
        redisService.set(SeckillKey.getVerifyCode, userId + "_" + goodsId, verifyCode);
        return VerifyUtil.createVerifyCodeImg(expression);
    }

    /**
     * 检查验证码
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @param verifyCode 验证码
     * @return true 检验成功，false 检验失败
     */
    @Override
    public boolean checkVerifyCode(long userId, long goodsId, int verifyCode) {
        Integer redisCode = redisService.get(SeckillKey.getVerifyCode, userId + "_" + goodsId, Integer.class);
        if (ObjectUtils.isEmpty(redisCode))
            throw new IllegalArgumentException("The verify code is expired");
        if (redisCode.equals(verifyCode)) {
            redisService.delete(SeckillKey.getVerifyCode, userId + "_" + goodsId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建用户秒杀路径
     * @param userId  用户ID
     * @param goodsId 商品ID
     */
    @Override
    public String createSeckillPath(long userId, long goodsId) {
        String seckillPath = MD5Util.md5(UUID.randomUUID().toString());
        redisService.set(SeckillKey.getSeckillPath, userId + "_" + goodsId, seckillPath);
        return seckillPath;
    }

    /**
     * 检查用户秒杀路径
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @param path    秒杀路径
     * @return true 合法，true，非法
     */
    @Override
    public boolean checkSeckillPath(long userId, long goodsId, String path) {
        String redisPath = redisService.get(SeckillKey.getSeckillPath, userId + "_" + goodsId, String.class);
        if (StringUtils.isBlank(path))
            throw new IllegalArgumentException("The URI of sec kill request does not exist");
        redisService.delete(SeckillKey.getSeckillPath, userId + "_" + goodsId);
        return path.equals(redisPath);
    }

}
