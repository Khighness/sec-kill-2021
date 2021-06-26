package top.parak.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote web配置
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AccessInterceptor accessInterceptor;
    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 认证
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/goods/detail")
                .addPathPatterns("/seckill/**")
                .addPathPatterns("/order/detail");
        // 限流
        registry.addInterceptor(accessInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
