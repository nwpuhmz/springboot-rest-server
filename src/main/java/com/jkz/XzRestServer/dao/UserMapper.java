package com.jkz.XzRestServer.dao;

import com.jkz.XzRestServer.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scuhmz on 2017/9/16.
 */
@Component
public interface UserMapper {
    @Select("select * from t_user where id = #{id}")
    @Results(id = "userMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "user_account"),
            @Result(property = "password", column = "user_password"),
            @Result(property = "roles", column = "id",many = @Many(select = "com.jkz.XzRestServer.dao.UserRoleMapper.findUserRolesByUid",fetchType = FetchType.EAGER))
    })
    User findUserById(@Param("id") int id);

    @Select("select * from t_user where user_account = #{username}")
    @ResultMap("userMap")
    User findUserByUsername(@Param("username") String username);

    @Select("select u.id,u.user_account from t_user u ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "user_account")
    })
    List<User> findAllUsers();

    @Select("select * from t_user limit #{offset}, #{perPage}")
    @ResultMap("userMap")
    List<User> findUsersByPage(@Param("offset") int offset,@Param("perPage") int perPage);

    @Insert("insert into t_user(user_account,user_password) values(#{username},#{password})")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertUser(User user);

    @Update("update t_user set user_account=#{username},user_password=#{password} where id=#{id}")
    int update(User user);

    @Delete("delete from t_user where id=#{id}")
    int delete(@Param("id") int id);

    @DeleteProvider(type = UserSqlBuilder.class, method = "deleteByids")
    int deleteByIds(@Param("ids") int[] ids);

    class UserSqlBuilder {


        //删除的方法
        public String deleteByids(@Param("ids") final int[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM t_user WHERE id in(");
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    sql.append(ids[i]);
                } else {
                    sql.append(ids[i]).append(",");
                }
            }
            sql.append(")");
            return sql.toString();
        }
    }
    @Select("select count(*) from t_user")
    int selectCount();
}

