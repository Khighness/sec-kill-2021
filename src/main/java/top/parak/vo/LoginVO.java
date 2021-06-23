package top.parak.vo;

import org.hibernate.validator.constraints.Length;
import top.parak.validator.IsMobile;

import javax.validation.constraints.NotNull;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 登录VO
 */
public class LoginVO {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVO[" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ']';
    }
}
