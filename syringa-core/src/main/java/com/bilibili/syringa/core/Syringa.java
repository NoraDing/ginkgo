/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.bilibili.syringa.core.statistics.ResultManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.job.JobManager;
import com.bilibili.syringa.core.job.MessageGenerator;
import com.google.common.base.Preconditions;

/**
 * @author dingsainan
 * @version $Id: Syringa.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
public class Syringa {
    private static final Logger LOGGER = LoggerFactory.getLogger(Syringa.class);

    public static void main(String[] args) {
//        args = new String[]{"-type", "2", "-message", "20000", "-concurrency", "20", "-size",
//                "10=4k,20=10k,30=60k,40=700K", "-topics", "syringa_one",
//                "-bootstrap.servers",
//                "172.22.33.99:9092,172.22.33.97:9092",
//                "-configPath",
//                "/Users/bilibili/soft/kafka-2.1.1/config/producer.properties",
//                "-blacklistZkPath",
//                "172.22.33.94:2181,172.22.33.99:2181,172.22.33.97:2181/nvm-t-kafka",
//                "-minBatchSize",
//                "1",
//                "-maxBatchSize",
//                "1",
//                "-minRecordSize",
//                "1",
//                "-maxRecordSize",
//                "1"
//
//        };

        try {
            Preconditions.checkArgument(args != null && args.length > 0);
            SyringaContext syringaContext = SyringaContext.getInstance();

            //1.启动参数解析
            OptionInit optionInit = new OptionInit(args);
            optionInit.startAsync().awaitRunning();
            syringaContext.setOptionInit(optionInit);

            //2.初始化消费发送器
            MessageGenerator messageGenerator = new MessageGenerator(syringaContext);
            messageGenerator.startAsync().awaitRunning();
            syringaContext.setMessageGenerator(messageGenerator);

            //3.初始化执行结果集
            syringaContext.setRunResults(new ArrayList<>());

            CountDownLatch countDownLatch = new CountDownLatch(
                    syringaContext.getSyringaSystemConfig().getConcurrency());
            syringaContext.setCountDownLatch(countDownLatch);

            //4.启动作业管理
            JobManager jobManager = new JobManager();
            jobManager.startAsync().awaitRunning();

            //TODO 4.收集统计数据
            ResultManager resultManager = new ResultManager();
            resultManager.startAsync().awaitRunning();

            LOGGER.info(" -------------finish------------");
        } catch (Exception e) {
            LOGGER.error("has issue here", e);
        }
    }
}