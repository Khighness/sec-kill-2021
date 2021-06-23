package top.parak.domain;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 商品
 */
public class Goods implements Serializable {
    private static final long serialVersionUID = -217690806513674563L;

    /**
     * ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 商品价格
     */
    private Double goodsPrice;

    /**
     * 商品库存
     * <p>-1代表没有限制
     */
    private Integer goodsStock;

    public Goods() {
    }

    public Goods(Long id, String goodsName, String goodsTitle, String goodsImg, String goodsDetail, Double goodsPrice, Integer goodsStock) {
        this.id = id;
        this.goodsName = goodsName;
        this.goodsTitle = goodsTitle;
        this.goodsImg = goodsImg;
        this.goodsDetail = goodsDetail;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Goods.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("goodsName='" + goodsName + "'")
                .add("goodsTitle='" + goodsTitle + "'")
                .add("goodsImg='" + goodsImg + "'")
                .add("goodsDetail='" + goodsDetail + "'")
                .add("goodsPrice=" + goodsPrice)
                .add("goodsStock=" + goodsStock)
                .toString();
    }
}
