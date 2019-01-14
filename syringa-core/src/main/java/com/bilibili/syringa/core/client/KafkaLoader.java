/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.client;

import java.util.concurrent.LinkedBlockingDeque;

import com.bilibili.syringa.core.dispatcher.AppDispatcher;
import com.bilibili.syringa.core.dto.LoaderConfigDTO;
import com.bilibili.syringa.core.enums.ApplicationEnums;
import com.bilibili.syringa.core.executor.ConsumerExecutor;
import com.bilibili.syringa.core.executor.Executor;
import com.bilibili.syringa.core.executor.ProducerExecutor;

/**
 * @author dingsainan
 * @version $Id: KafkaLoader.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
public class KafkaLoader {

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

        //register and start dispatcher
        LoaderConfigDTO loaderConfigDTO = new LoaderConfigDTO(appId, appNumber, reqNumber, pkgType,
            partitionNumber, nMessage, packageScale, appConfig);
        registerAndStartDispatcher(appType, loaderConfigDTO);

    }

    private static void registerAndStartDispatcher(String appType,
                                                   LoaderConfigDTO loaderConfigDTO) {

        LinkedBlockingDeque<Executor> executors = new LinkedBlockingDeque<>();

        if (ApplicationEnums.PRODUCER.getType() == Integer.valueOf(appType)) {
            executors.add(new ConsumerExecutor());

        }

        if (ApplicationEnums.CONSUMER.getType() == Integer.valueOf(appType)) {
            executors.add(new ProducerExecutor());

        }
        AppDispatcher appDispatcher = new AppDispatcher(loaderConfigDTO, executors);

        appDispatcher.serviceStart();

    }

}