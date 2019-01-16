/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.job.JobManager;
import com.bilibili.syringa.core.job.MessageGenerator;
import com.bilibili.syringa.core.statistics.ResultManager;
import com.bilibili.syringa.core.statistics.RunResult;
import com.bilibili.syringa.core.util.ThreadPoolExecutorFactory;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.AsyncEventBus;

/**
 * @author dingsainan
 * @version $Id: Syringa.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
public class Syringa {
    private static final Logger LOGGER = LoggerFactory.getLogger(Syringa.class);

    public static void main(String[] args) {

        try {

            Preconditions.checkArgument(args != null && args.length > 0);

            SyringaContext syringaContext = SyringaContext.getInstance();

            //1.启动参数解析
            OptionInit optionInit = new OptionInit(args);
            optionInit.startAsync().awaitRunning();

            syringaContext.setOptionInit(optionInit);

            //2.初始化消费发送器
            MessageGenerator messageGenerator = new MessageGenerator(
                syringaContext.getSyringaSystemConfig().getJobMessageConfigList());
            syringaContext.setMessageGenerator(messageGenerator);
            messageGenerator.startAsync().awaitRunning();

            //3.启动作业管理
            JobManager jobManager = new JobManager(syringaContext.getSyringaSystemConfig(),
                syringaContext.getMessageGenerator());

            jobManager.startAsync().awaitRunning();
            List<Future<RunResult>> futureResults = jobManager.run();

            //4.收集统计数据
            ResultManager resultManager = new ResultManager(futureResults);
            AsyncEventBus asyncEventBus = new AsyncEventBus(
                ThreadPoolExecutorFactory.newThreadPoolExecutor("ResultManager-counter"));//todo
            asyncEventBus.register(resultManager);
            syringaContext.setAsyncEventBus(asyncEventBus);

            resultManager.startAsync().awaitRunning();

            //5.关闭各个服务
            optionInit.stopAsync().awaitRunning();
            messageGenerator.stopAsync().awaitRunning();
            jobManager.stopAsync().awaitRunning();
            resultManager.stopAsync().awaitRunning();

            LOGGER.info("finish ------------");
            System.exit(1);

        } catch (Exception e) {
            LOGGER.error("has issue here", e);
        }

    }
}