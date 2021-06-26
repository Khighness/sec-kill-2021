package top.parak.mvc;

import top.parak.vo.UserVO;

/**
 * @author KHighness
 * @since 2021-06-26
 * @apiNote 保存用户信息
 */
public class UserContext {
    private static ThreadLocal<UserVO> userHolder = new ThreadLocal<>();

    public static void setUser(UserVO userVO) {
        userHolder.set(userVO);
    }

    public static UserVO getUser() {
        return userHolder.get();
    }
}
