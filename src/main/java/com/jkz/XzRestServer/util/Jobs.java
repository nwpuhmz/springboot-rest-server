package com.jkz.XzRestServer.util;

import com.jkz.XzRestServer.dao.YuyueMapper;
import com.jkz.XzRestServer.model.Yuyue;
import com.jkz.XzRestServer.service.YuyueService;
import com.jkz.XzRestServer.websocket.Message;
import com.jkz.XzRestServer.websocket.SocketServer;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by scuhmz on 2017/11/27.
 */
@Component
public class Jobs {

    private final SocketServer socketServer;
    private final YuyueService yuyueService;
    @Autowired
    public Jobs(SocketServer socketServer,YuyueService yuyueService){
        this.socketServer = socketServer;
        this.yuyueService = yuyueService;
    }

    public final static long ONE_Minute =  60 * 1000;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


//    @Scheduled(fixedRate=1000)
//    public void fixedRateJob(){
//        System.out.println(dateFormat.format(new Date()) + ">>fixedRate执行....");
//        socketServer.sendBroadcast(SocketServer.NamespaceEnum.yuyue, "message", new Message("i'm server"));
//
//    }

    @Scheduled(fixedRate=1000*60)
    public void YuyueJob(){
        System.out.println(dateFormat.format(new Date()) + ">>YuyueJob 执行....");
        List<Yuyue> yuyueList = yuyueService.findAllYuyue();
        List<Yuyue> resultList;
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateNowStr = sdf.format(new Date());
        resultList = yuyueList.stream()
                .filter(yuyue -> dateNowStr.equals(sdf.format(yuyue.getUpdateTime())))
                .collect(Collectors.toList());
        socketServer.sendBroadcast(SocketServer.NamespaceEnum.yuyue, "message", resultList.toString());

    }
}
