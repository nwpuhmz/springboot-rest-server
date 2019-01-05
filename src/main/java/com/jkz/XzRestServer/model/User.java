package com.jkz.XzRestServer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by scuhmz on 2017/9/16.
 */
@Accessors(chain = true)
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1773498078549665904L;
    @ApiModelProperty(notes = "用户ID，主键自增")
    private int id;
    @ApiModelProperty(notes = "用户账号")
    private String username;
    @ApiModelProperty(notes = "用户密码")
    private String password;
    @ApiModelProperty(notes = "用户权限列表")
    private List<String> roles;

}
