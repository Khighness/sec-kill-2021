package top.parak.redis;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote 秒杀键
 */
public class SeckillKey extends BasePrefix {

    public SeckillKey(String prefix) {
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("over");

}
