/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author dingsainan
 * @version $Id: JobManager.java, v 0.1 2019-01-14 上午11:23 dingsainan Exp $$
 */
public class JobManager {

    public void submit(JobContext jobContext) {

        try {
            List<Future<Object>> futures = MoreExecutors.newDirectExecutorService().invokeAll(null);


        } catch (InterruptedException e) {

        }

    }

}