package top.parak.mvc;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 过滤器配置
 */
@Configuration
public class FilterConfigure {

    /**
     * 注册过滤器1
     */
    @Bean
    public ParaFirstFilter para1Filter() {
        return new ParaFirstFilter();
    }

    /**
     * 注册过滤器2
     */
    @Bean
    public ParaSecondFilter para2Filter() {
        return new ParaSecondFilter();
    }

    /**
     * 添加过滤器1
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean1() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(para1Filter());
        registration.setEnabled(true);
        registration.setOrder(1);
        registration.addUrlPatterns("/index/*/*");
        return registration;
    }

    /**
     * 添加过滤器2
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(para2Filter());
        registration.setEnabled(true);
        registration.setOrder(2);
        registration.addUrlPatterns("/index/*/*");
        return registration;
    }
}
