package top.parak.service;

import top.parak.vo.GoodsVO;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 商品服务接口
 */
public interface GoodsService {

    public List<GoodsVO> getGoodsVOList();

    public GoodsVO getGoodsVOById(long goodsId);

    public boolean reduceStockCountById(long goodsId, int reduceCount);

}
