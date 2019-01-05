package com.jkz.XzRestServer.dao;

import com.jkz.XzRestServer.model.Item;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by scuhmz on 2017/9/16.
 */
@Component
public interface ItemMapper {
    @Select("select * from t_item where id = #{id}")
    @Results(id = "itemMap", value = {
            @Result(property = "id", column = "id",javaType=Integer.class,jdbcType= JdbcType.INTEGER),
            @Result(property = "flight", column = "item_flight",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property = "flightFx_Hx_DM", column = "item_flightFx_Hx_DM",javaType=String.class,jdbcType= JdbcType.VARCHAR),
            @Result(property = "flightDate", column = "item_flightDate",javaType=java.sql.Date.class,jdbcType=JdbcType.DATE),
            @Result(property = "flight_count", column = "item_flight_count",javaType=Integer.class,jdbcType= JdbcType.INTEGER),
            @Result(property = "start_end_time", column = "item_start_end_time",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property = "ssd_number", column = "item_ssd_number",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property = "file_number", column = "item_file_number",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property = "status", column = "item_status",javaType=Integer.class,jdbcType= JdbcType.INTEGER),
            @Result(property = "dataType", column = "item_dataType",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property = "item_desc", column = "item_item_desc",javaType=String.class,jdbcType=JdbcType.VARCHAR),
            @Result(property = "user_id", column = "user_id",javaType=Integer.class,jdbcType= JdbcType.INTEGER),
            @Result(property = "user", column = "user_id",one=@One(select = "com.jkz.XzRestServer.dao.UserMapper.findUserById",fetchType = FetchType.EAGER)),
            @Result(property = "createTime", column = "create_time",javaType=java.sql.Timestamp.class,jdbcType= JdbcType.TIMESTAMP),
            @Result(property = "updateTime", column = "update_time",javaType=java.sql.Timestamp.class,jdbcType= JdbcType.TIMESTAMP)
})
    Item findItemById(@Param("id") int id);

    @Insert("INSERT INTO t_item(user_id,item_flight,item_flightFx_Hx_DM,item_flightDate,item_flight_count,item_start_end_time," +
            "item_ssd_number,item_file_number,item_status,item_dataType,item_item_desc) " +
            "VALUES (#{user_id},#{flight}, #{flightFx_Hx_DM},#{flightDate},#{flight_count},#{start_end_time},#{ssd_number}," +
            "#{file_number},#{status},#{dataType},#{item_desc}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Item item);

    @Update("UPDATE t_item " +
            "SET user_id=#{user_id} " +
            ",item_flight=#{flight}, " +
            "item_flightFx_Hx_DM=#{flightFx_Hx_DM}, " +
            "item_flightDate=#{flightDate}, " +
            "item_flight_count=#{flight_count}, " +
            "item_start_end_time=#{start_end_time}, "+
            "item_ssd_number=#{ssd_number}, " +
            "item_file_number=#{file_number}, " +
            "item_dataType=#{dataType}, " +
            "item_item_desc=#{item_desc} WHERE id=#{id}")
    int update(Item item);

    @Select("select * from t_item")
    @ResultMap("itemMap")
    List<Item> findAllItems();

    @Select("select * from t_item limit #{offset}, #{perPage}")
    @ResultMap("itemMap")
    List<Item> findItemsByPage(@Param("offset") int offset,@Param("perPage") int perPage);

    @Select("select count(*) from t_item")
    int selectCount();

    @Delete("delete from t_item where id=#{id}")
    int delete(@Param("id") int id);

    @DeleteProvider(type = ItemSqlBuilder.class, method = "deleteByids")
    int deleteByIds(@Param("ids") int[] ids);

    class ItemSqlBuilder {
//        public String queryLearnResouceByParams(final Map<String, Object> params) {
//            StringBuffer sql =new StringBuffer();
//            sql.append("select * from learn_resource where 1=1");
//            if(!StringUtil.isNull((String)params.get("author"))){
//                sql.append(" and author like '%").append((String)params.get("author")).append("%'");
//            }
//            if(!StringUtil.isNull((String)params.get("title"))){
//                sql.append(" and title like '%").append((String)params.get("title")).append("%'");
//            }
//            System.out.println("查询sql=="+sql.toString());
//            return sql.toString();
//        }

        //删除的方法
        public String deleteByids(@Param("ids") final int[] ids){
            StringBuffer sql =new StringBuffer();
            sql.append("DELETE FROM t_item WHERE id in(");
            for (int i=0;i<ids.length;i++){
                if(i==ids.length-1){
                    sql.append(ids[i]);
                }else{
                    sql.append(ids[i]).append(",");
                }
            }
            sql.append(")");
            return sql.toString();
        }
    }
}

