package top.parak.service;

import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

import java.awt.image.BufferedImage;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀服务接口
 */
public interface SeckillService {

    public void kill(UserVO user, GoodsVO goods);

    public long getSeckillResult(long userId, long goodsId);

    public void setGoodsOver(long goodsId);

    public boolean getGoodsOver(long goodsId);

    public BufferedImage createVerifyCode(long userId, long goodsId);

    public boolean checkVerifyCode(long userId, long goodsId, int verifyCode);

    public String createSeckillPath(long userId, long goodsId);

    public boolean checkSeckillPath(long userId, long goodsId, String path);

}
