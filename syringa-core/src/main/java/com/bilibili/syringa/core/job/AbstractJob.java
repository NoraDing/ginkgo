/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

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

    protected MessageGenerator         messageGenerator;
    protected Properties               properties;

    public AbstractJob(String name, long messageCounter, MessageGenerator messageGenerator,
                       Properties properties) {
        this.name = name;
        this.messageCounter = messageCounter;
        this.messageGenerator = messageGenerator;
        this.properties = properties;

    }

    @Override
    protected void startUp() throws Exception {

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
        this.topicList = SyringaContext.getInstance().getSyringaSystemConfig().getTopicList();
        this.asyncEventBus = SyringaContext.getInstance().getAsyncEventBus();
        this.messageGenerator = SyringaContext.getInstance().getMessageGenerator();
        this.properties = SyringaContext.getInstance().getSyringaSystemConfig().getProperties();

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
