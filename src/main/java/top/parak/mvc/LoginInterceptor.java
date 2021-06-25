package top.parak.mvc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.parak.exception.GlobalException;
import top.parak.redis.AuthKey;
import top.parak.redis.RedisService;
import top.parak.response.CodeMessage;
import top.parak.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (authenticated(request))
            return true;
        else {
            log.info("拦截URL => {}", request.getRequestURL());
            // response.sendRedirect("/user/login");
            throw new GlobalException(CodeMessage.TOKEN_ERROR);
        }
    }

    /**
     * 根据cookie判断认证状态
     * @return true 已认证
     */
    private boolean authenticated(HttpServletRequest request) {
        String token = CookieUtil.getTokenFromParamOrCookies(request);
        if (StringUtils.isBlank(token))
            return false;
        return redisService.exists(AuthKey.token, token);
    }
}
