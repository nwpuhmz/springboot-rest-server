package com.jkz.XzRestServer.dao;

import com.jkz.XzRestServer.model.Item;
import com.jkz.XzRestServer.model.dto.CountDto;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by scuhmz on 2017/9/16.
 */
@Component
public interface CountMapper {

    /*
    SELECT
	DATE_FORMAT(item_flightDate, '%Y-%m') months,
	item_flightFx_Hx_DM,count(DISTINCT item_flight,item_flightFx_Hx_DM,item_flightDate,item_flight_count) as num
    FROM
        t_item
    WHERE YEAR(item_flightDate)='2017'
    GROUP BY
        months,item_flightFx_Hx_DM;

     */
    @Select("SELECT\n" +
            "\tDATE_FORMAT(item_flightDate, '%Y-%m') months,\n" +
            "\titem_flightFx_Hx_DM,count(DISTINCT item_flight,item_flightFx_Hx_DM,item_flightDate,item_flight_count) as num\n" +
            "FROM\n" +
            "\tt_item\n" +
            "WHERE YEAR(item_flightDate)=#{year} AND item_flight=#{flight}\n" +
            "GROUP BY\n" +
            "\tmonths,item_flightFx_Hx_DM;\n")
    @Results({
            @Result(property = "year_and_month", column = "months"),
            @Result(property = "flightFx_Hx_DM",  column = "item_flightFx_Hx_DM"),
            @Result(property = "flightFx_Hx_DM_count",  column = "num")
})
    List<CountDto> CountItemFxHxDmByYear(@Param("year") String year,@Param("flight") String flight);


    @Select("SELECT\n" +
            "\tDATE_FORMAT(item_flightDate, '%Y-%m') months,\n" +
            "\titem_flightFx_Hx_DM,count(DISTINCT item_flight,item_flightFx_Hx_DM,item_flightDate,item_flight_count) as num\n" +
            "FROM\n" +
            "\tt_item\n" +
            "WHERE YEAR(item_flightDate)=#{year} AND user_id=#{user_id}\n" +
            "GROUP BY\n" +
            "\tmonths,item_flightFx_Hx_DM;\n")
    @Results({
            @Result(property = "year_and_month", column = "months"),
            @Result(property = "flightFx_Hx_DM",  column = "item_flightFx_Hx_DM"),
            @Result(property = "flightFx_Hx_DM_count",  column = "num")
    })
    List<CountDto> CountItemFxHxDmByYearAndUid(@Param("year") String year,@Param("user_id") int user_id);

    List<CountDto> CountItemFxHxDmBy(@Param("year") String year,@Param("user_id") int user_id);
}

