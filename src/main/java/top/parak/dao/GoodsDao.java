package top.parak.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import top.parak.vo.GoodsVO;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 商品持久接口
 */
@Mapper
@Repository
public interface GoodsDao {

    @Select("select g.*, skg.seckill_price, skg.stock_count, skg.start_date, skg.end_date " +
            "from seckill_goods skg left join goods g on skg.goods_id = g.id")
    public List<GoodsVO> getGoodsVOList();

    @Select("select g.*, skg.seckill_price, skg.stock_count, skg.start_date, skg.end_date " +
            "from seckill_goods skg left join goods g on skg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVO getGoodsVOById(@Param("goodsId") long goodsId);

    @Update("update seckill_goods set stock_count = stock_count - ${reduceCount} " +
            "where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStockCountById(@Param("goodsId") long goodsId, @Param("reduceCount") int reduceCount);

}
