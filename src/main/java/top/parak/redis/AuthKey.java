package top.parak.redis;

import top.parak.util.CookieUtil;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 认证键
 */
public class AuthKey extends BasePrefix {

    public AuthKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AuthKey token = new AuthKey(CookieUtil.COOKIE_TOKEN_MAX_AGE, CookieUtil.COOKIE_TOKEN_NAME);

}
