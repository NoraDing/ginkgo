/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.job.JobManager;
import com.bilibili.syringa.core.statistics.ResultManager;
import com.bilibili.syringa.core.statistics.RunResult;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;

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

            //2.启动作业管理
            JobManager jobManager = new JobManager(syringaContext.getSyringaSystemConfig());

            jobManager.startAsync().awaitRunning();
            List<Future<RunResult>> run = jobManager.run();

            while (!syringaContext.isFinish()) {
                TimeUnit.MILLISECONDS.sleep(500);
            }

            //            ResultManager resultManager = new ResultManager();

            optionInit.stopAsync().awaitRunning();
            jobManager.stopAsync().awaitRunning();

            LOGGER.info("finish ------------");
            System.exit(1);

        } catch (Exception e) {
            LOGGER.error("has issue here", e);
        }

    }
}