/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.util;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author xuezhaoming
 * @version $Id: ThreadPoolExecutorFactory.java, v 0.1 2019-01-15 4:35 PM Exp $$
 */
public final class ThreadPoolExecutorFactory {

    public static ThreadPoolExecutor newThreadPoolExecutor(String name) {

        /**
         * 生成线程池
         */
        return new ThreadPoolExecutor(8, 16, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
            new BasicThreadFactory.Builder().namingPattern("name-%d").daemon(true).build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    }
}
