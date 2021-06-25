package top.parak.redis;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 用户键
 */
public class UserKey extends BasePrefix {

    public UserKey(String prefix) {
        super(prefix);
    }

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByMobile = new UserKey("mobile");

    public static UserKey getByNickname = new UserKey("nickname");
}
