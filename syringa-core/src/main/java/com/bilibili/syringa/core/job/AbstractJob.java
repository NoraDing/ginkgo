/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import com.bilibili.syringa.core.SyringaConfig;
import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.util.ThreadPoolExecutorFactory;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author xuezhaoming
 * @version $Id: AbstractJob.java, v 0.1 2019-01-15 2:30 PM Exp $$
 */
public abstract class AbstractJob extends AbstractIdleService implements Job {

    private static final Logger        LOGGER = LoggerFactory.getLogger(AbstractJob.class);

    protected String                   name;

    protected long                     messageCounter;

    protected List<String>             topicList;

    protected SyringaSystemConfig      syringaSystemConfig;

    protected ListeningExecutorService listeningExecutorService;

    protected AsyncEventBus            asyncEventBus;

    public AbstractJob(String name, long messageCounter) {
        this.name = name;
        this.messageCounter = messageCounter;

    }

    @Override
    protected void startUp() throws Exception {

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
        this.topicList = SyringaContext.getInstance().getSyringaSystemConfig().getTopicList();
        this.asyncEventBus = SyringaContext.getInstance().getAsyncEventBus();

        LOGGER.info("init executor success !");
    }

    @Override
    protected void shutDown() throws Exception {

        if (listeningExecutorService != null) {
            listeningExecutorService.shutdown();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public long getMessageCounter() {
        return messageCounter;
    }

}
