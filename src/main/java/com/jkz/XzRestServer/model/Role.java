package com.jkz.XzRestServer.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by scuhmz on 2017/9/18.
 */
@Accessors(chain = true)
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -3927954821681442717L;
    private int id;
    private String roleName;
}
