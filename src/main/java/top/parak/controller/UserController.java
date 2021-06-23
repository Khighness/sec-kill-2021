package top.parak.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.parak.domain.User;
import top.parak.response.ServerResponse;
import top.parak.service.UserService;
import top.parak.vo.LoginVO;
import top.parak.vo.UserVO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @return 返回登录页面
     */
    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    /**
     * 认证
     * @param response 请求
     * @param loginVO  登录表单
     * @return 0代表成功，其他代表失败
     */
    @ResponseBody
    @PostMapping("/authenticate")
    public ServerResponse<String> authenticate(HttpServletResponse response, @Valid LoginVO loginVO) {
        userService.authenticate(response, loginVO);
        return ServerResponse.success(null);
    }

    /**
     * 信息
     * @param user 当前用户信息
     * @return 过滤敏感信息的用户信息
     */
    @ResponseBody
    @GetMapping("/info")
    public ServerResponse<UserVO> info(User user) {
        UserVO userVO = new UserVO(user.getId(), user.getMobile(), user.getNickname(), user.getAvatar(), user.getRegisterDate(), user.getLastLoginDate(), user.getLoginCount());
        return ServerResponse.success(userVO);
    }

}
