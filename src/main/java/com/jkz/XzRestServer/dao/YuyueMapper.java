package com.jkz.XzRestServer.dao;

import com.jkz.XzRestServer.model.Role;
import com.jkz.XzRestServer.model.Yuyue;
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
public interface YuyueMapper {

    @Select("select * from t_yuyue")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "flight_no",  column = "yuyue_flight_no"),
            @Result(property = "flihgt_jiaci",  column = "yuyue_flihgt_jiaci"),
            @Result(property = "flight_FxHxDm",  column = "yuyue_flight_FxHxDm"),
            @Result(property = "beizhu",  column = "yuyue_beizhu"),
            @Result(property = "createTime",  column = "create_time"),
            @Result(property = "updateTime",  column = "update_time"),

    })
    List<Yuyue> findAllYuyue();
}

