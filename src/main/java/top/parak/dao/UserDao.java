package top.parak.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.parak.domain.User;
import top.parak.vo.UserVO;

import java.util.Date;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 用户持久接口
 */
@Mapper
@Repository
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") long id);

    @Select("select * from user where mobile = #{mobile}")
    public User getByMobile(@Param("mobile") String mobile);

    @Select("select id, mobile, nickname, avatar, register_date, last_login_date, login_count from user where id = #{id}")
    public UserVO getUserVOById(@Param("id") long id);

    @Insert("insert into user(id, mobile, nickname, password, salt, avatar, register_date, last_login_date, login_count) " +
            "values(#{id}, #{mobile}, #{nickname}, #{password}, #{salt}, #{avatar}, #{registerDate}, #{lastLoginDate}, #{loginCount})")
    public int saveUser(User user);

    @Update("update user set last_login_date = #{lastLoginDate}, login_count = login_count + 1 where mobile = #{mobile}")
    public void updateLogin(@Param("mobile") String mobile, @Param("lastLoginDate") Date lastLoginDate);

}
