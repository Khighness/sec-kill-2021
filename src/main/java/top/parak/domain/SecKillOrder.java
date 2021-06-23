package top.parak.domain;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀订单
 */
public class SecKillOrder implements Serializable {
    private static final long serialVersionUID = -7240713949003615761L;

    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private Long goodsId;

    public SecKillOrder() {
    }

    public SecKillOrder(Long id, Long userId, Long orderId, Long goodsId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.goodsId = goodsId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SecKillOrder.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("orderId=" + orderId)
                .add("goodsId=" + goodsId)
                .toString();
    }
}
