package top.parak.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.parak.dao.UserDao;
import top.parak.domain.User;
import top.parak.exception.GlobalException;
import top.parak.redis.AuthKey;
import top.parak.redis.RedisService;
import top.parak.redis.UserKey;
import top.parak.response.CodeMessage;
import top.parak.service.UserService;
import top.parak.util.CookieUtil;
import top.parak.util.MD5Util;
import top.parak.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;

    @Override
    public void authenticate(HttpServletResponse response, LoginVO loginVO) {
        if (ObjectUtils.isEmpty(loginVO))
            throw new NullPointerException("Login View Object cannot be null");
        String mobile = loginVO.getMobile();
        // 判断存在
        User user = userDao.getByMobile(mobile);
        if (ObjectUtils.isEmpty(user))
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXISTS);
        // 验证密码
        String inPass = loginVO.getPassword();
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        if (!MD5Util.tranPassToDBPass(inPass, dbSalt).equals(dbPass))
            throw new GlobalException(CodeMessage.PASSWORD_WRONG);
        // 生成token
        String token = UUID.randomUUID().toString();
        addCookieAndSaveRedis(response, token, user);
        log.info("登录成功 => [电话: {}, 昵称: {}]", user.getMobile(), user.getNickname());
        // 更新登录
        userDao.updateLogin(mobile, new Date());
    }

    @Override
    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isBlank(token))
            return null;
        User user = redisService.get(AuthKey.token, token, User.class);
        if (!ObjectUtils.isEmpty(user))
            addCookieAndSaveRedis(response, token, user);
        return user;
    }

    @Override
    public User getById(long id) {
        User user = redisService.get(UserKey.getById, "" + id, User.class);
        if (!ObjectUtils.isEmpty(user)) {
            return user;
        }
        user = getById(id);
        redisService.set(UserKey.getById, "" + id, user);
        return user;
    }

    @Override
    public User getByMobile(String mobile) {
        return userDao.getByMobile(mobile);
    }

    @Override
    public boolean saveUser(User user) {
        int ans = userDao.saveUser(user);
        return ans > 0;
    }

    @Override
    public boolean updatePassword(String token, String mobile, String newPass) {
        // 更新数据库
        User user = getByMobile(mobile);
        if (ObjectUtils.isEmpty(user))
            throw new GlobalException(CodeMessage.MOBILE_NOT_EXISTS);
        user.setPassword(newPass);
        String targetPass = MD5Util.inputPassToDBPass(newPass, user.getSalt());
        userDao.updatePassword(mobile, targetPass);
        // 更新缓存
        redisService.delete(UserKey.getById, "" + user.getId());
        redisService.delete(UserKey.getByMobile, "" + user.getMobile());
        redisService.set(AuthKey.token, token, user);
        return true;
    }

    /**
     * 向客户端和缓存中添加token
     */
    private void addCookieAndSaveRedis(HttpServletResponse response, String token, User user) {
        redisService.set(AuthKey.token, token, user);
        CookieUtil.addTokenToCookies(response, token);
    }

}
