package top.parak.redis;

/**
 * @author KHighness
 * @since 2021-06-23
 * @apiNote 商品键
 */
public class GoodsKey extends BasePrefix {
    private static final int CACHE_TIME = 600;

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getList = new GoodsKey(CACHE_TIME, "list");

    public static GoodsKey getDetailById = new GoodsKey(CACHE_TIME, "id");

}
