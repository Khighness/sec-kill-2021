package top.parak.service;

import top.parak.domain.User;
import top.parak.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 用户服务接口
 */
public interface UserService {

    public void authenticate(HttpServletResponse response, LoginVO loginVO);

    public User getByToken(HttpServletResponse response, String token);

    public User getById(Long id);

    public User getByMobile(String mobile);

    public boolean saveUser(User user);
}
