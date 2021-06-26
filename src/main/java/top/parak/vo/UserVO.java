package top.parak.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author KHighness
 * @since 2021-06-23
 * @apiNote 用户VO
 */
public class UserVO implements Serializable {
    private static final long serialVersionUID = 7220119027878694126L;

    private Long id;

    private String mobile;

    private String nickname;

    private String avatar;

    private Date registerDate;

    private Date lastLoginDate;

    private Long loginCount;

    public UserVO() {
    }

    public UserVO(Long id, String mobile, String nickname, String avatar, Date registerDate, Date lastLoginDate, Long loginCount) {
        this.id = id;
        this.mobile = mobile;
        this.nickname = nickname;
        this.avatar = avatar;
        this.registerDate = registerDate;
        this.lastLoginDate = lastLoginDate;
        this.loginCount = loginCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserVO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("mobile='" + mobile + "'")
                .add("nickname='" + nickname + "'")
                .add("avatar='" + avatar + "'")
                .add("registerDate=" + registerDate)
                .add("lastLoginDate=" + lastLoginDate)
                .add("loginCount=" + loginCount)
                .toString();
    }
}
