package com.jkz.XzRestServer.constant;

import lombok.NoArgsConstructor;

/**
 * Created by scuhmz on 2017/9/17.
 */
@NoArgsConstructor
public class ErrorCode {
    public static final int SERVER_INTERNAL_ERROR = 1000;
    public static final int PARAMETER_MISSING_ERROR = 1001;
    public static final int PARAMETER_ILLEGAL_ERROR = 1002;
    public static final int RESOURCE_NOT_FOUND_ERROR = 1003;
}
