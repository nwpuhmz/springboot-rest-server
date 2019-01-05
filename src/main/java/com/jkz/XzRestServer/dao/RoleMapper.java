package com.jkz.XzRestServer.dao;

import com.jkz.XzRestServer.model.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scuhmz on 2017/9/18.
 */
@Component
public interface RoleMapper {

    @Select("select * from t_role r WHERE r.role_name= #{roleName}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "roleName",  column = "role_name")
    })
    Role findRoleByRoleName(@Param("roleName") String roleName);

//    @Insert("insert into t_user_role(user_id,role_id) values(#{uid},#{rid})")
//    @Options(useGeneratedKeys=true,keyProperty="id")

//    int insertRoleByUid(@Param("uid") int id,@Param("rid") int rid);
}
