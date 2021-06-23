package top.parak.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀商品
 */
public class SeckillGoods implements Serializable {
    private static final long serialVersionUID = -2387777416641646152L;

    /**
     * ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 秒杀价格
     */
    private Double seckillPrice;

    /**
     * 库存数量
     */
    private Integer stockCount;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    public SeckillGoods() {
    }

    public SeckillGoods(Long id, Long goodsId, Double seckillPrice, Integer stockCount, Date startDate, Date endDate) {
        this.id = id;
        this.goodsId = goodsId;
        this.seckillPrice = seckillPrice;
        this.stockCount = stockCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SeckillGoods.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("goodsId=" + goodsId)
                .add("seckillPrice=" + seckillPrice)
                .add("stockCount=" + stockCount)
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
