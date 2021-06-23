package top.parak.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.parak.domain.Goods;

import java.util.Date;
import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 商品VO
 */
public class GoodsVO extends Goods {
    private static final long serialVersionUID = -2467984244064140627L;

    private Double seckillPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

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
        return new StringJoiner(", ", Goods.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("goodsName='" + getGoodsName() + "'")
                .add("goodsTitle='" + getGoodsTitle() + "'")
                .add("goodsImg='" + getGoodsImg() + "'")
                .add("goodsDetail='" + getGoodsDetail() + "'")
                .add("goodsPrice=" + getGoodsPrice())
                .add("goodsStock=" + getGoodsStock())
                .add("seckillPrice=" + seckillPrice)
                .add("stockCount=" + stockCount)
                .add("startDate=" +  startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
