package top.parak.vo;

/**
 * @author KHighness
 * @since 2021-06-24
 * @apiNote 商品详情VO
 */
public class GoodsDetailVO {

    private GoodsVO goods;

    private int seckillStatus;

    private int remainSeconds;


    public GoodsDetailVO(GoodsVO goods, int seckillStatus, int seckillSeconds) {
        this.goods = goods;
        this.seckillStatus = seckillStatus;
        this.remainSeconds = seckillSeconds;
    }

    public GoodsVO getGoods() {
        return goods;
    }

    public void setGoods(GoodsVO goods) {
        this.goods = goods;
    }

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }
}
