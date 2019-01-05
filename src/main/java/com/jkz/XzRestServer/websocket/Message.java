package com.jkz.XzRestServer.websocket;

import lombok.Data;

/**
 * Created by scuhmz on 2017/12/18.
 */
@Data
public class Message {
    String message;
    public Message(String message)
    {this.message = message;}

}

