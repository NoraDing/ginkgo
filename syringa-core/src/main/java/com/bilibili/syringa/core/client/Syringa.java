/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.client;

import java.util.concurrent.LinkedBlockingDeque;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.enums.ApplicationEnums;
import com.bilibili.syringa.core.executor.ConsumerExecutor;
import com.bilibili.syringa.core.executor.Executor;
import com.bilibili.syringa.core.executor.ProducerExecutor;
import com.bilibili.syringa.core.job.JobConfig;

/**
 * @author dingsainan
 * @version $Id: Syringa.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
public class Syringa {

    public static void main(String[] args) {

        //压测的客户端，接受压测参数
        //1.判断接收参数的个数

        //2.每个参数的含义
        String appId = args[0];
        String appType = args[1];
        String appNumber = args[2];
        String reqNumber = args[3];
        String pkgType = args[4];
        String partitionNumber = args[5];
        String nMessage = args[6];
        String packageScale = args[7]; //sample="10:1M;20:3M;30:512k;40:218K"
        String appConfig = args[8]; //To be determined

        //1.参数解析 args

        SyringaSystemConfig syringaSystemConfig = new SyringaSystemConfig();
        SyringaContext.getInstance().setSyringaSystemConfig(syringaSystemConfig);

        //2.初始化job 
        JobConfig jobConfig = new JobConfig();

        //3.作业运行

        //4.收集统计信息

        //register and start dispatcher
        //        SyringaSystemConfig syringaSystemConfig = new SyringaSystemConfig(appId, appNumber,
        //            reqNumber, pkgType, partitionNumber, nMessage, packageScale, appConfig);
        //        registerAndStartDispatcher(appType, syringaSystemConfig);

    }

    private static void registerAndStartDispatcher(String appType,
                                                   SyringaSystemConfig syringaSystemConfig) {

        LinkedBlockingDeque<Executor> executors = new LinkedBlockingDeque<>();

        if (ApplicationEnums.PRODUCER.getType() == Integer.valueOf(appType)) {
            executors.add(new ConsumerExecutor());

        }

        if (ApplicationEnums.CONSUMER.getType() == Integer.valueOf(appType)) {
            executors.add(new ProducerExecutor());

        }
        //        JobDispatcher appDispatcher = new JobDispatcher(syringaSystemConfig, executors);

        //        appDispatcher.serviceStart();

    }

}