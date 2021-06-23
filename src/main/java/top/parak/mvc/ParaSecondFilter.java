package top.parak.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 过滤器2
 */
public class ParaSecondFilter implements Filter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("过滤器2处理: Protocol => {}, Encoding => {}", servletRequest.getProtocol(), servletRequest.getCharacterEncoding());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
