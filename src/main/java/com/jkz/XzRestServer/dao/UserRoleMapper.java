package com.jkz.XzRestServer.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scuhmz on 2017/9/18.
 */
@Component
public interface UserRoleMapper {

    @Select("SELECT r.role_name FROM t_user_role ur LEFT JOIN t_role r ON ur.role_id = r.id WHERE ur.user_id= #{uid}")
    @Results({
            @Result(property = "roleName",  column = "role_name"),
    })
    List<String> findUserRolesByUid(@Param("uid") int id);

    @Insert("insert into t_user_role(user_id,role_id) values(#{uid},#{rid})")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertRoleByUid(@Param("uid") int id,@Param("rid") int rid);

    @Delete("delete from t_user_role where user_id = #{uid}")
    int deleteUserRolesByUid(@Param("uid") int id);
}
