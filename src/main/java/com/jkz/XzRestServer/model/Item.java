package com.jkz.XzRestServer.model;

import com.sun.tools.corba.se.idl.constExpr.Times;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by scuhmz on 2017/11/2.
 */
@Accessors(chain = true)
@Data
public class Item implements Serializable{
    private static final long serialVersionUID = 2423568976305337792L;
    @ApiModelProperty(notes = "卸载记录ID，主键自增")
    private int id;
    @ApiModelProperty(notes = "用户ID")
    private int user_id;
    @ApiModelProperty(notes = "飞机号")
    private String flight;
    @ApiModelProperty(notes = "架次类型")
    private String flightFx_Hx_DM;
    @ApiModelProperty(notes = "架次日期")
    private Date flightDate;
    @ApiModelProperty(notes = "架次编号")
    private int flight_count;
    @ApiModelProperty(notes = "起止时间")
    private String start_end_time;
    @ApiModelProperty(notes = "数据固态盘编号")
    private String ssd_number;
    @ApiModelProperty(notes = "架次文件号")
    private String file_number;
    @ApiModelProperty(notes = "卸载状态")
    private int status;
    @ApiModelProperty(notes = "数据类型")
    private String dataType;
    @ApiModelProperty(notes = "备注")
    private String item_desc;
    @ApiModelProperty(notes = "创建日期")
    private Timestamp createTime;
    @ApiModelProperty(notes = "修改日期")
    private Timestamp updateTime;

    User user;
}
