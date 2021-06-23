package top.parak.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 用户
 */
public class User implements Serializable {
    private static final long serialVersionUID = 765441092957231182L;

    /**
     * ID
     */
    private Long id;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册日期
     */
    private Date registerDate;

    /**
     * 最后一次登录日期
     */
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    private Long loginCount;

    public User() {
    }

    public User(Long id, String mobile, String nickname, String password, String salt, String avatar, Date registerDate, Date lastLoginDate, Long loginCount) {
        this.id = id;
        this.mobile = mobile;
        this.nickname = nickname;
        this.password = password;
        this.salt = salt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
        return "User{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", avatar='" + avatar + '\'' +
                ", registerDate=" + registerDate +
                ", lastLoginDate=" + lastLoginDate +
                ", loginCount=" + loginCount +
                '}';
    }
}
