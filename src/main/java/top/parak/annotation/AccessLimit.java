package top.parak.annotation;

import java.lang.annotation.*;

/**
 * @author KHighness
 * @since 2021-06-26
 * @apiNote 接口限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    int seconds();

    int maxCount();
}
