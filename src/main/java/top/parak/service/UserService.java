package top.parak.service;

import org.apache.commons.lang3.StringUtils;
import top.parak.domain.User;
import top.parak.vo.LoginVO;
import top.parak.vo.UserVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 用户服务接口
 */
public interface UserService {

    public void authenticate(HttpServletResponse response, LoginVO loginVO);

    public UserVO getByToken(HttpServletResponse response, String token);

    public User getById(long id);

    public User getByMobile(String mobile);

    public boolean saveUser(User user);

    public boolean updatePassword(String token, String mobile, String newPass);

}
