package com.jkz.XzRestServer.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by scuhmz on 2017/9/18.
 */
@Accessors(chain = true)
@Data
public class UserRole implements Serializable{
    private static final long serialVersionUID = 5152069224473690962L;
    private int id;
    private int userId;
    private int roleId;
}


