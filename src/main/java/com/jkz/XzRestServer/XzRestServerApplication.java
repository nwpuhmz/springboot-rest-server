package com.jkz.XzRestServer;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.jkz.XzRestServer.util.FileListener;
import com.jkz.XzRestServer.websocket.SocketServer;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan   //扫描Servlet
@MapperScan("com.jkz.XzRestServer.dao")
@ComponentScan
//@EnableAutoConfiguration
public class XzRestServerApplication {


//	@Value("${rt.server.host}")
//	private String host;
//
//	@Value("${rt.server.port}")
//	private Integer port;
//
//	@Bean
//	public SocketIOServer socketIOServer() {
//		Configuration config = new Configuration();
//		config.setHostname(host);
//		config.setPort(port);
//		return new SocketIOServer(config);
//	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(XzRestServerApplication.class, args);
//		File directory = new File("D:/test");
//		// 轮询间隔 5 秒
//		long interval = TimeUnit.SECONDS.toMillis(5);
//		// 创建一个文件观察器用于处理文件的格式
//		FileAlterationObserver observer = new FileAlterationObserver(directory, FileFilterUtils.and(
//				FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt")));
//		observer.addListener(new FileListener()); //设置文件变化监听器
//		//创建文件变化监听器
//		FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
//		// 开始监控
//		monitor.start();
		/********************************** Websocket server **********************************************/
		SocketServer server = new SocketServer();
		server.run();

	}
}
