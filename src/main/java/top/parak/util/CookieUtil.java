package top.parak.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote Cookie工具类
 */
public class CookieUtil {
    /**
     * cookie的token键名
     */
    public static final String COOKIE_TOKEN_NAME = "token";
    /**
     * cookie的token保存时间
     */
    public static final int COOKIE_TOKEN_MAX_AGE = 3600 * 24 * 7;

    /**
     * 从参数或者cookie中获取token
     * @param request 请求
     * @return token
     */
    public static String getTokenFromParamOrCookies(HttpServletRequest request) {
        String token = Objects.requireNonNull(request).getParameter(COOKIE_TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            token = CookieUtil.getValueFromCookies(request, COOKIE_TOKEN_NAME);
        }
        return token;
    }

    /**
     * 从请求中获取cookie的值
     * @param request    请求
     * @param cookieName cookie的键
     * @return cookie的值
     */
    public static String getValueFromCookies(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 向客户端响应中添加token的cookie
     * @param response 响应
     * @param token    令牌
     */
    public static void addTokenToCookies(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_TOKEN_NAME, token);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_TOKEN_MAX_AGE);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
