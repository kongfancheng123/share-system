package com.share.system.data;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.util.Properties;


@SpringBootApplication
@EnableScheduling
@EnableWebSocketMessageBroker
@MapperScan("com.share.system.data.dao")
public class RunnerApplication implements CommandLineRunner {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RunnerApplication.class);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Runnable runnable = () -> {
            while (true) {
                try {
                   Thread.sleep(20000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }





    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "postgresql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
