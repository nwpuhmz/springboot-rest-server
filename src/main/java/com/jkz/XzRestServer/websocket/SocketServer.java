package com.jkz.XzRestServer.websocket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by scuhmz on 2017/12/18.
 */
@Component
public class SocketServer {
    private static Logger logger= LoggerFactory.getLogger(SocketServer.class);

    private static SocketIOServer server = initServer();
    /**
     * 初始化服务端
     * @return
     */
    private static SocketIOServer initServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(8012);
        server = new SocketIOServer(config);
        return server;
    }
    /**
     * 启动服务端
     */
    /**
     * 启动服务
     */
    public void run(){

        // 循环添加命名空间
        for(NamespaceEnum namespace : NamespaceEnum.values()){
            server.addNamespace("/" + namespace.toString());
        }

        // 某个命名空间下添加事件监听
        final SocketIONamespace yuyuenamespace = server.addNamespace("/yuyue");
        yuyuenamespace.addEventListener("message", Message.class, new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                // broadcast messages to all clients
                data = new Message("hello i'm server");
                //yuyuenamespace.getBroadcastOperations().sendEvent("message", data);
            }
        });

//        server.getNamespace("/yuyue").addEventListener("connect", Object.class, new DataListener<Object>() {
//            @Override
//            public void onData(SocketIOClient client, Object data, AckRequest ackRequest) {
//                logger.info("on Data");
//                // broadcast messages to all clients
//                System.out.println(data.toString());
//                data = "hello ,i'm server";
//                server.getNamespace("/yuyue").getBroadcastOperations().sendEvent("server-connect", data);
//            }
//        });

        // 启动服务
        server.start();

        // Thread.sleep(Integer.MAX_VALUE);

        // 停止服务
        // server.stop();
    }
    /** 发布广播
     * @param namespace 命名空间
     * @param eventName 事件名称
     * @param data 自定义数据
     */
    public void sendBroadcast(NamespaceEnum namespace, String eventName, Object data){
        server.getNamespace("/" + namespace).getBroadcastOperations().sendEvent(eventName, data);

    }

    /**
     * 命名空间枚举
     */
    public enum NamespaceEnum{
        yuyue
    }
    /**
     * 停止服务端
     */
    public void stopServer() {
        server.stop();
    }
}