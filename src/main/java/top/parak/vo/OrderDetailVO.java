package top.parak.vo;

import top.parak.domain.OrderInfo;

import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote 订单VO
 */
public class OrderDetailVO {
    private GoodsVO goodsVO;
    private OrderInfo orderInfo;

    public OrderDetailVO(GoodsVO goodsVO, OrderInfo orderInfo) {
        this.goodsVO = goodsVO;
        this.orderInfo = orderInfo;
    }

    public GoodsVO getGoodsVO() {
        return goodsVO;
    }

    public void setGoodsVO(GoodsVO goodsVO) {
        this.goodsVO = goodsVO;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderDetailVO.class.getSimpleName() + "[", "]")
                .add("goodsVO=" + goodsVO)
                .add("orderInfo=" + orderInfo)
                .toString();
    }
}
