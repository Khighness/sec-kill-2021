package top.parak.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 校验工具类
 */
public class ValidateUtil {
    /**
     * 电话正则表达式
     */
    private static final String MOBILE_REGEX = "^0\\d{2,3}-\\d{7,9}|1[3|4|5|7|8|9]\\d{9}$";
    /**
     * 邮箱正则表达式
     */
    private static final String EMAIL_REGEX = "^[\\u4e00-\\u9fa5-\\w]+@[-\\w]+(\\.[-\\w]+)*\\.[a-z]{2,4}$";

    private static final Pattern MOBILE_PATTERN = Pattern.compile(MOBILE_REGEX);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * 检验字符串是否为电话格式
     * @param mobile 电话字符串
     * @return true代表是，false代表不是
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile))
            return false;
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }

    /**
     * 检验字符串是否为邮箱格式
     * @param email 邮箱字符串
     * @return true代表是，false代表不是
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email))
            return false;
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
