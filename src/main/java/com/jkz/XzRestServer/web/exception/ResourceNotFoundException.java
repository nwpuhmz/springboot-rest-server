package com.jkz.XzRestServer.web.exception;


import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

/**
 * Created by scuhmz on 2017/9/17.
 */
@Accessors(chain = true)
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4579358751637800125L;
    private String resourceName;
    //private Integer id;

    @Override
    public String getMessage() {
        return StringUtils.capitalize(resourceName)  + " is not found.";
    }
}
