package com.jkz.XzRestServer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by scuhmz on 2017/12/25.
 */
@Accessors(chain = true)
@Data
public class Yuyue implements Serializable {
    private static final long serialVersionUID = -2839759464305792274L;
    @ApiModelProperty(notes = "预约记录ID，主键自增")
    private int id;
    @ApiModelProperty(notes = "飞机号")
    private String flight_no;
    @ApiModelProperty(notes = "架次号")
    private int flihgt_jiaci;
    @ApiModelProperty(notes = "滑行/飞行/地面")
    private String flight_FxHxDm;
    @ApiModelProperty(notes = "备注")
    private String beizhu;
    @ApiModelProperty(notes = "创建日期")
    private Timestamp createTime;
    @ApiModelProperty(notes = "修改日期")
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "Yuyue{" +
                "id=" + id +
                ", flight_no='" + flight_no + '\'' +
                ", flihgt_jiaci=" + flihgt_jiaci +
                ", flight_FxHxDm='" + flight_FxHxDm + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
