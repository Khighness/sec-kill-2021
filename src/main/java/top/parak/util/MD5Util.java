package top.parak.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote MD5消息摘要
 */
public class MD5Util {
    /**
     * 生成随机盐可使用字符
     */
    private static final char[] SALT_TABLE =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
             'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
             'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
             'u', 'v', 'w', 'x', 'y', 'z'};
    /**
     * 传输密码盐值前缀
     */
    private static final String TRANSPORT_PASSWORD_SALT_PREFIX = "1823676372";
    /**
     * 传输密码盐值后缀
     */
    private static final String PASSWORD_SALT_SUFFIX = "khighness";
    /**
     * 消息摘要算法实例
     */
    private static MessageDigest messageDigest;

    // 初始化为MD5
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字符串的MD5消息摘要
     * @param src 原始字符串
     * @return MD5消息摘要
     */
    public static String md5(String src)  {
        byte[] digest = messageDigest.digest(src.getBytes());
        return Hex.encodeHexString(digest);
    }

    /**
     * 生成指定长度的随机盐值
     * @param len 盐值长度
     * @return 盐值
     */
    public static String salt(int len) {
        if (len < 6 || len > 20)
            throw new IllegalArgumentException("Salt's length should be greater than 5 and less than 21");
        StringBuilder builder = new StringBuilder();
        final int n = SALT_TABLE.length;
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < len; i++) {
            builder.append(SALT_TABLE[random.nextInt(n)]);
        }
        return builder.toString();
    }

    /**
     * 将输入密码转换为传输密码
     * @param inputPass 输入密码
     * @return 表单密码
     */
    public static String inputPassToTranPass(String inputPass) {
        StringBuilder builder = new StringBuilder();
        builder.append(TRANSPORT_PASSWORD_SALT_PREFIX).append(inputPass).append(PASSWORD_SALT_SUFFIX);
        String updatePass = builder.toString();
        return md5(updatePass);
    }

    /**
     * 将传输密码转换为数据库密码
     * @param tranPass 传输密码
     * @param salt     随机盐值
     * @return 数据库密码
     */
    public static String tranPassToDBPass(String tranPass, String salt) {
        int len = salt.length();
        if (len <= 0)
            throw new IllegalArgumentException("Salt's length should be greater than 1");
        StringBuilder builder = new StringBuilder();
        builder.append(salt.substring(len - 1)).append(tranPass).append(salt.substring(len));
        String updatePass = builder.toString();
        return md5(updatePass);
    }

    /**
     * 将输入密码转换为数据库密码
     * @param inputPass 表单密码
     * @param saltDB    随机盐值
     * @return 数据库密码
     */
    public static String inputPassToDBPass(String inputPass, String saltDB) {
        // assert saltDB.length() >= 1
        return tranPassToDBPass(inputPassToTranPass(inputPass), saltDB);
    }

}
