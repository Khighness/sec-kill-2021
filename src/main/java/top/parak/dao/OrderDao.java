package top.parak.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 订单持久化
 */
@Mapper
@Repository
public interface OrderDao {

    @Select("select * from seckill_order where user_id = #{userId} and goods_id = #{goodsId}")
    public SecKillOrder getSeckillOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, delivery_addr_id, goods_name, goods_count, goods_price, order_channel, status, create_date) " +
            "values(#{userId}, #{goodsId}, #{deliveryAddrId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "select last_insert_id()")
    public long insertOrderAndGetId(OrderInfo orderInfo);

    @Insert("insert into seckill_order(user_id, order_id, goods_id) values(#{userId}, #{orderId}, #{goodsId})")
    public long insertSeckillOrder(SecKillOrder secKillOrder);

}
