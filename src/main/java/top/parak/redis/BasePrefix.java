package top.parak.redis;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 键前缀
 */
public abstract class BasePrefix implements KeyPrefix {
    /**
     * 过期时间
     */
    private int expireSeconds = -1;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * <p>默认过期时间-1，代表永久有效
     */
    public BasePrefix(String prefix) {
        this(-1, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName + SPLIT + prefix;
    }

}
