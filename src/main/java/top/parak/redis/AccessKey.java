package top.parak.redis;

/**
 * @author KHighness
 * @since 2021-06-26
 * @apiNote 访问键
 */
public class AccessKey extends BasePrefix {

    public AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 过期时间5秒，代表5秒之内不能重复访问
     */
    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}
