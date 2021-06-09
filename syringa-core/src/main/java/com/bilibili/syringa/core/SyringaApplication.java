/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author dingsainan
 * @version $Id: Syringa.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
@Configuration
@ImportResource({ "classpath:ApplicationContext.xml" })
@SpringBootApplication
public class SyringaApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyringaApplication.class);

    public static void main(String[] args) {
        LOGGER.info("application is starting...");
        final ConfigurableApplicationContext context;
        try {
            context = SpringApplication.run(SyringaApplication.class);
        } catch (Exception e) {
            System.err.println("Failed to start " + SyringaApplication.class);
            e.printStackTrace(System.err);
            System.exit(-1);
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> context.close()));

        LOGGER.info("application start ok...");


        //        args = new String[]{"-type", "2", "-message", "20", "-concurrency", "1", "-size",
//                "10=4k,20=10k,30=60k,40=700K", "-topics", "lancer_live_web_playerheart",
//                "-bootstrap.servers",
//                "172.22.33.99:9092,172.22.33.97:9092",
//                "-configPath",
//                "/Users/dingsainan/soft/kafka_2.11-2.1.1/config/producer.properties",
//                "-b-lacklistZkPath",
//                "172.22.33.94:2181,172.22.33.99:2181,172.22.33.97:2181/nvm-t-kafka",
//                "-minBatchSize",
//                "1",
//                "-maxBatchSize",
//                "1",
//                "-minRecordSize",
//                "1",
//                "-maxRecordSize",
//                "1"
//        };
//
//        try {
//            Preconditions.checkArgument(args != null && args.length > 0);
//            SyringaContext syringaContext = SyringaContext.getInstance();
//
//            //1.启动参数解析
//            OptionInit optionInit = new OptionInit(args);
//            optionInit.startAsync().awaitRunning();
//            syringaContext.setOptionInit(optionInit);
//
//            //2.初始化消费发送器
//            MessageGenerator messageGenerator = new MessageGenerator(syringaContext);
//            messageGenerator.startAsync().awaitRunning();
//            syringaContext.setMessageGenerator(messageGenerator);
//
//            //3.初始化执行结果集
//            syringaContext.setRunResults(new ArrayList<>());
//
//            CountDownLatch countDownLatch = new CountDownLatch(
//                    syringaContext.getSyringaSystemConfig().getConcurrency());
//            syringaContext.setCountDownLatch(countDownLatch);
//
//            //4.启动作业管理
//            JobManager jobManager = new JobManager();
//            jobManager.startAsync().awaitRunning();
//
//            //TODO 4.收集统计数据
//            ResultManager resultManager = new ResultManager();
//            resultManager.startAsync().awaitRunning();
//
//            LOGGER.info(" -------------finish------------");
//        } catch (Exception e) {
//            LOGGER.error("has issue here", e);
//        }
    }

}