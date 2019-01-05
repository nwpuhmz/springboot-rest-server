package com.jkz.XzRestServer.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by scuhmz on 2017/9/17.
 */
@Data
@Accessors(chain = true)
public class Error implements Serializable {
    private static final long serialVersionUID = 8422402446871811756L;
    private int code;
    private String message;
}
