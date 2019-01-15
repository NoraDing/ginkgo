/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;

import com.bilibili.syringa.core.config.SyringaSystemConfig;

import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author dingsainan
 * @version $Id: JobManager.java, v 0.1 2019-01-14 上午11:23 dingsainan Exp $$
 */
public class JobManager extends AbstractIdleService {

    private SyringaSystemConfig syringaSystemConfig;

    public JobManager(SyringaSystemConfig syringaSystemConfig) {
        this.syringaSystemConfig = syringaSystemConfig;
    }

    @Override
    protected void startUp() throws Exception {

        //转配成作业的配置

        //运行作业
    }

    @Override
    protected void shutDown() throws Exception {

    }
}