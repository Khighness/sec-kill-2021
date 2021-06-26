package top.parak.rabbit;

import top.parak.domain.User;
import top.parak.vo.UserVO;

import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote 秒杀消息
 */
public class SeckillMessage {

    private UserVO userVO;
    private Long goodsId;

    public SeckillMessage(UserVO userVO, Long goodsId) {
        this.userVO = userVO;
        this.goodsId = goodsId;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SeckillMessage.class.getSimpleName() + "[", "]")
                .add("userVO=" + userVO)
                .add("goodsId=" + goodsId)
                .toString();
    }
}
