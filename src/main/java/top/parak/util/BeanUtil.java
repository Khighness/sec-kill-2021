package top.parak.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote Bean工具类
 */
public class BeanUtil {

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
            return JSON.parseObject(str, clazz);
    }

    public static void main(String[] args) {
        System.out.println(beanToString("Khighness"));
    }
}
