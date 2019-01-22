/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.job.JobManager;
import com.bilibili.syringa.core.job.MessageGenerator;
import com.bilibili.syringa.core.statistics.ResultManager;
import com.bilibili.syringa.core.statistics.RunResult;
import com.google.common.base.Preconditions;

/**
 * @author dingsainan
 * @version $Id: Syringa.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
public class Syringa {
    private static final Logger LOGGER = LoggerFactory.getLogger(Syringa.class);

    public static void main(String[] args) {

        try {

            args = new String[] { "-type", "1", "-message", "10000000", "-concurrency", "5",
                                  "-size", "10=4k,20=1k,30=6k,40=7k", "-topics",
                                  "syringa_one,syringa_two,syringa_three,syringa_four,syringa_five",
                                  "-bootstrap.servers",
                                  "10.23.58.106:9093,10.23.58.111:9093,10.23.58.119:9093",
                                  "-configPath",
                                  "/Users/dingsainan/soft/kafka_2.11-2.1.0/config/producer.properties" };
            Preconditions.checkArgument(args != null && args.length > 0);

            SyringaContext syringaContext = SyringaContext.getInstance();

            //1.启动参数解析
            OptionInit optionInit = new OptionInit(args);
            optionInit.startAsync().awaitRunning();

            syringaContext.setOptionInit(optionInit);

            //2.初始化消费发送器
            MessageGenerator messageGenerator = new MessageGenerator(
                syringaContext.getSyringaSystemConfig().getJobMessageConfigList());
            messageGenerator.startAsync().awaitRunning();
            syringaContext.setMessageGenerator(messageGenerator);

            long messages = syringaContext.getSyringaSystemConfig().getMessages();
            int concurrency = syringaContext.getSyringaSystemConfig().getConcurrency();
            Properties properties = syringaContext.getSyringaSystemConfig().getProperties();
            TypeEnums type = syringaContext.getSyringaSystemConfig().getType();
            List<String> topicList = syringaContext.getSyringaSystemConfig().getTopicList();

            //3.启动作业管理
            JobManager jobManager = new JobManager(type, messages, concurrency, topicList,
                properties, syringaContext.getMessageGenerator());

            jobManager.startAsync().awaitRunning();
            List<Future<List<RunResult>>> futureResults = jobManager.run();

            //4.收集统计数据
            ResultManager resultManager = new ResultManager(futureResults);
            resultManager.startUp();
            resultManager.infoStatistics();

            LOGGER.info("finish ------------");
            System.exit(1);

        } catch (Exception e) {
            LOGGER.error("has issue here", e);
        }

    }
}