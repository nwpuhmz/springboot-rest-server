package com.jkz.XzRestServer.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by scuhmz on 2017/9/19.
 */
@Accessors(chain = true)
@Data
public class UserDto {
    @ApiModelProperty(notes = "用户ID，主键自增")
    private int id;
    @ApiModelProperty(notes = "用户账号")
    private String username;
}
