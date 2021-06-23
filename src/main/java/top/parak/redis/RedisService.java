package top.parak.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 封装RedisTemplate
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取值
     * @param prefix 前缀
     * @param key    键
     * @param clazz  类型
     * @param <T>    泛型
     * @return T类型的对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        if (clazz == null)
            throw new NullPointerException("Clazz cannot be null");
        String realKey = getRealKey(prefix, key);
        Object obj = redisTemplate.opsForValue().get(realKey);
        return (T) obj;
    }

    /**
     * 设置键值对
     * @param prefix 前缀
     * @param key    键
     * @param value  值
     * @param <T>    泛型
     * @return true 设置成功
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        String realKey = getRealKey(prefix, key);
        if (value == null)
            throw new NullPointerException("Value cannot be null");
        int expireSeconds = prefix.expireSeconds();
        if (expireSeconds < -1)
            throw new IllegalArgumentException("Expire Seconds cannot be less than -1");
        if (expireSeconds == -1) {
            redisTemplate.opsForValue().set(realKey, value);
        } else {
            redisTemplate.opsForValue().set(realKey, value, expireSeconds, TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 判断键是否存在
     * @param prefix 前缀
     * @param key    键
     * @param <T>    泛型
     * @return true 存在，false 不存在
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        String realKey = getRealKey(prefix, key);
        return redisTemplate.opsForValue().getOperations().hasKey(realKey);
    }


    /**
     * 获取键的超时时间
     * <li>expireTime = -1，代表永不超时
     * <li>expireTime = -2，代表已经超时
     * <li>expireTime > 0，代表剩余有效时间
     * @param prefix 前缀
     * @param key    键
     * @param <T>    泛型
     * @return 键的超时时间
     */
    public <T> long expireTime(KeyPrefix prefix, String key) {
        String realKey = getRealKey(prefix, key);
        return redisTemplate.opsForValue().getOperations().getExpire(realKey, TimeUnit.SECONDS);
    }

    /**
     * 删除键值对
     * @param prefix 前缀
     * @param key    键
     * @return true 成功，false 失败
     */
    public boolean delete(KeyPrefix prefix, String key) {
        String realKey = getRealKey(prefix, key);
        return redisTemplate.opsForValue().getOperations().delete(realKey);
    }

    /**
     * 键值自增
     * @param prefix 前缀
     * @param key    键
     * @return 自增后的值
     */
    public Long increment(KeyPrefix prefix, String key) {
        String realKey = getRealKey(prefix, key);
        return redisTemplate.opsForValue().increment(realKey, 1);
    }

    /**
     * 键值自减
     * @param prefix 前缀
     * @param key    键
     * @return 自减后的值
     */
    public Long decrement(KeyPrefix prefix, String key) {
        String realKey = getRealKey(prefix, key);
        return redisTemplate.opsForValue().decrement(realKey, 1);
    }

    public static String getRealKey(KeyPrefix prefix, String key) {
        if (ObjectUtils.isEmpty(prefix) || StringUtils.isBlank(key))
            throw new NullPointerException("Prefix and key cannot be null");
        return prefix.getPrefix() + key;
    }

    @SuppressWarnings("unchecked")
    public static <T> String beanToString(T value) {
        if (value == null)
            return null;
        Class<?> clazz = value.getClass();
        if (clazz == Integer.class || clazz == Long.class)
            return String.valueOf(value);
        else if (clazz == String.class)
            return (String) value;
        else
            return JSON.toJSONString(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || ObjectUtils.isEmpty(clazz))
            return null;
        if (clazz == Integer.class)
            return (T) Integer.valueOf(str);
        else if (clazz == Long.class)
            return (T) Long.valueOf(str);
        else if (clazz == String.class)
            return (T) str;
        else
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }

}
