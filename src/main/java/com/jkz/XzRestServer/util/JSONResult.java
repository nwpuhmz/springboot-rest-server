package com.jkz.XzRestServer.util;

import org.json.JSONObject;

/**
 * Created by scuhmz on 2017/9/17.
 */
public class JSONResult {
    public static String fillResultString(Integer code, String message, Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("code", code);
            put("message", message);
            put("result", result);
        }};

        return jsonObject.toString();
    }
}
