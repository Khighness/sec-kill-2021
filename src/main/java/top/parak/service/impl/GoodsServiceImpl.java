package top.parak.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.parak.dao.GoodsDao;
import top.parak.service.GoodsService;
import top.parak.vo.GoodsVO;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 商品服务实现
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<GoodsVO> getGoodsVOList() {
        return goodsDao.getGoodsVOList();
    }

    @Override
    public GoodsVO getGoodsVOById(long goodsId) {
        return goodsDao.getGoodsVOById(goodsId);
    }

    @Override
    public boolean reduceStockCountById(long goodsId, int reduceCount) {
        int res = goodsDao.reduceStockCountById(goodsId, reduceCount);
        return res == 1;
    }
}
