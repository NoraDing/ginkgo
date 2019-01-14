/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.dispatcher;

import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.collections.CollectionUtils;

import com.bilibili.syringa.core.dto.LoaderConfigDTO;
import com.bilibili.syringa.core.executor.Executor;

/**
 *
 * @author dingsainan
 * @version $Id: AppDispatcher.java, v 0.1 2019-01-14 上午11:22 dingsainan Exp $$
 */
public class AppDispatcher {
    private LoaderConfigDTO                     loaderConfigDTO;

    //存放所有事件的队列（生产/消费）
    private final LinkedBlockingDeque<Executor> executors;

    public AppDispatcher(LoaderConfigDTO loaderConfigDTO, LinkedBlockingDeque<Executor> executors) {
        this.loaderConfigDTO = loaderConfigDTO;
        this.executors = executors;

    }

    public void serviceStart() {

        //开始executor的过程
        while (CollectionUtils.isNotEmpty(executors)) {
            Executor executor = executors.poll();
            executor.execute();

        }

    }

}