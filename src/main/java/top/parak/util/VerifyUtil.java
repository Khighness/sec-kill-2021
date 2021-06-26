package top.parak.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author KHighness
 * @since 2021-06-26
 * @apiNote 验证码工具类
 */
public class VerifyUtil {
    private static final char[] OPS = {'+', '-', '*'};
    private static final int VERIFY_CODE_IMAGE_WIDTH = 80;
    private static final int VERIFY_CODE_IMAGE_HEIGHT = 32;

    /**
     * 创建表达式验证码图片
     * @param expression 表达式
     * @return 验证码图片
     */
    public static BufferedImage createVerifyCodeImg(String expression) {
        BufferedImage image = new BufferedImage(VERIFY_CODE_IMAGE_WIDTH, VERIFY_CODE_IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        // 背景
        graphics.setColor(new Color(0XDCDCDC));
        graphics.fillRect(0, 0, VERIFY_CODE_IMAGE_WIDTH, VERIFY_CODE_IMAGE_HEIGHT);
        // 边缘
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, VERIFY_CODE_IMAGE_WIDTH - 1, VERIFY_CODE_IMAGE_HEIGHT - 1);
        // 画点
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(VERIFY_CODE_IMAGE_WIDTH);
            int y = random.nextInt(VERIFY_CODE_IMAGE_HEIGHT);
            graphics.drawOval(x, y, 0, 0);
        }
        // 表达式
        graphics.setColor(new Color(0, 100, 0));
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        graphics.drawString(expression, 8, 24);
        graphics.dispose();
        return image;
    }

    /**
     * 创建表达式字符串
     * @return 表达式字符串
     */
    public static String generateExpression() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        builder.append(random.nextInt(10))
                .append(OPS[random.nextInt(3)])
                .append(random.nextInt(10))
                .append(OPS[random.nextInt(3)])
                .append(random.nextInt(10));
        return builder.toString();
    }


    /**
     * 计算表达式
     * @param expression 表达式
     * @return 结果
     */
    public static int calculate(String expression) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (int) engine.eval(expression);
        } catch (Exception e) {
            return 0;
        }
    }

}
