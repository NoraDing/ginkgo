/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 *
 * @author xuezhaoming
 * @version $Id: AbstractJob.java, v 0.1 2019-01-15 2:30 PM Exp $$
 */
public abstract class AbstractJob extends AbstractIdleService implements Job {

    private static final Logger        LOGGER        = LoggerFactory.getLogger(AbstractJob.class);
    private static final int           corePoolSize  = 8;
    private static final int           maxPoolSize   = 20;
    private static final long          keepAliveTime = 10000l;
    private static final int           queueCapacity = 7000;
    protected SyringaContext           syringaContext;

    protected ListeningExecutorService listeningExecutorService;
    protected ThreadPoolExecutor       threadPoolExecutor;

    public AbstractJob() {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(queueCapacity),
            new BasicThreadFactory.Builder().namingPattern("job-manager-%d").daemon(true).build());

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
        syringaContext = SyringaContext.getInstance();
    }

    @Override
    protected void startUp() throws Exception {

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
        LOGGER.info("init executor success !");
    }

    @Override
    protected void shutDown() throws Exception {

        if (listeningExecutorService != null) {
            listeningExecutorService.shutdown();
        }
    }

}
