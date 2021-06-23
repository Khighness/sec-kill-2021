package top.parak.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 订单信息
 */
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 8916604259989819727L;

    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 地址ID
     */
    private Long deliveryAddrId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 下单数量
     */
    private Integer goodsCount;

    /**
     * 下单价格
     */
    private Double goodsPrice;

    /**
     * 下单渠道
     * <li>1: PC(电脑)
     * <li>2: AND(安卓)
     * <li>3: IOS(苹果)
     */
    private Short orderChannel;

    /**
     * 订单状态
     * <li>0: 已下单
     * <li>1: 已支付
     * <li>2: 已发货
     * <li>3: 已收货
     * <li>4: 已退款
     * <li>5: 已完成
     */
    private Short status;

    /**
     * 下单日期
     */
    private Date createDate;

    /**
     * 支付日期
     */
    private Date payDate;

    public OrderInfo() {
    }

    public OrderInfo(Long id, Long userId, Long goodsId, Long deliveryAddrId, String goodsName, Integer goodsCount, Double goodsPrice, Short orderChannel, Short status, Date createDate, Date payDate) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.deliveryAddrId = deliveryAddrId;
        this.goodsName = goodsName;
        this.goodsCount = goodsCount;
        this.goodsPrice = goodsPrice;
        this.orderChannel = orderChannel;
        this.status = status;
        this.createDate = createDate;
        this.payDate = payDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Short getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Short orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderInfo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("goodsId=" + goodsId)
                .add("deliveryAddrId=" + deliveryAddrId)
                .add("goodsName='" + goodsName + "'")
                .add("goodsCount=" + goodsCount)
                .add("goodsPrice=" + goodsPrice)
                .add("orderChannel=" + orderChannel)
                .add("status=" + status)
                .add("createDate=" + createDate)
                .add("payDate=" + payDate)
                .toString();
    }
}
