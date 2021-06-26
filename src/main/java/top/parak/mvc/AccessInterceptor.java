package top.parak.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import top.parak.annotation.AccessLimit;
import top.parak.redis.AccessKey;
import top.parak.redis.AuthKey;
import top.parak.redis.RedisService;
import top.parak.response.CodeMessage;
import top.parak.util.CookieUtil;
import top.parak.vo.UserVO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KHighness
 * @since 2021-06-26
 * @apiNote 访问拦截器
 */
@Component
public class AccessInterceptor implements AsyncHandlerInterceptor, InitializingBean {
    private static String ACCESS_LIMIT_REACHED_JSON = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        JSONObject json = new JSONObject();
        json.put("code", CodeMessage.ACCESS_LIMIT_REACHED.getCode());
        json.put("msg", CodeMessage.ACCESS_LIMIT_REACHED.getMsg());
        ACCESS_LIMIT_REACHED_JSON = json.toJSONString();
    }

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (!ObjectUtils.isEmpty(accessLimit)) {
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                String uri = request.getRequestURI();
                String token = CookieUtil.getTokenFromParamOrCookies(request);
                UserVO userVO = redisService.get(AuthKey.token, token, UserVO.class);
                String key = uri + "_" + userVO.getId();
                AccessKey accessKey = AccessKey.withExpire(seconds);
                Integer count = redisService.get(accessKey, key, Integer.class);
                if (count == null) {
                    redisService.set(accessKey, key, 1);
                } else if (count < maxCount) {
                    redisService.increment(accessKey, key);
                } else {
                    response.setContentType("application/json;charset=UTF-8");
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(ACCESS_LIMIT_REACHED_JSON.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    outputStream.close();
                    return false;
                }
            }
        }
        return true;
    }

}
