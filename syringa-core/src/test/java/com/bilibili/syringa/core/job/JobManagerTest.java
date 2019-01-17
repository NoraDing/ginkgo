/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.Properties;

import org.junit.Test;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.enums.TypeEnums;

/**
 *
 * @author dingsainan
 * @version $Id: JobManagerTest.java, v 0.1 2019-01-16 下午5:09 dingsainan Exp $$
 */
public class JobManagerTest {
    @Test
    public void jobTest() {

        String[] args = new String[] { "-type", "1", "-message", "1000", "-concurrency", "5",
                                       "-size", "10=4k,20=1k,30=6k,40=7k", "-topics",
                                       "topic1,topic2", "-bootstrap.servers", "localhost:9092",
                                       "-configPath",
                                       "/Users/dingsainan/soft/kafka_2.11-2.1.0/config/producer.properties" };

        SyringaContext syringaContext = SyringaContext.getInstance();

        //1.启动参数解析
        OptionInit optionInit = new OptionInit(args);
        optionInit.startAsync().awaitRunning();

        //2.初始化消费发送器
        MessageGenerator messageGenerator = new MessageGenerator(
            syringaContext.getSyringaSystemConfig().getJobMessageConfigList());
        messageGenerator.startAsync().awaitRunning();
        syringaContext.setMessageGenerator(messageGenerator);

        long messages = syringaContext.getSyringaSystemConfig().getMessages();
        int concurrency = syringaContext.getSyringaSystemConfig().getConcurrency();
        Properties properties = syringaContext.getSyringaSystemConfig().getProperties();
        TypeEnums type = syringaContext.getSyringaSystemConfig().getType();

        //3.启动作业管理
        JobManager jobManager = new JobManager(type, messages, concurrency, null, properties,
            syringaContext.getMessageGenerator());
        jobManager.startAsync().awaitRunning();

    }
}