package top.parak.redis;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 键前缀
 */
public interface KeyPrefix {

    public static final String SPLIT = ":";

    public int expireSeconds();

    public String getPrefix();

}
