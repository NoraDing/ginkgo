/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.enums.TypeEnums;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 * @author dingsainan
 * @version $Id: JobManager.java, v 0.1 2019-01-14 上午11:23 dingsainan Exp $$
 */
public class JobManager extends AbstractIdleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobManager.class);
    private static final int corePoolSize = 8;
    private static final int maxPoolSize = 20;
    private static final long keepAliveTime = 10000l;
    private static final int queueCapacity = 7000;
    private SyringaContext syringaContext;
    private TypeEnums type;
    private long messages;
    private int concurrency;
    private List<String> topicList;
    private Properties properties;
    private MessageGenerator messageGenerator;
    private ThreadPoolExecutor threadPoolExecutor;

    public JobManager() {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(queueCapacity),
                new BasicThreadFactory.Builder().namingPattern("job-manager-%d").daemon(true).build());
        this.syringaContext = SyringaContext.getInstance();
        this.messages = syringaContext.getSyringaSystemConfig().getMessages();
        this.concurrency = syringaContext.getSyringaSystemConfig().getConcurrency();
        this.properties = syringaContext.getSyringaSystemConfig().getProperties();
        this.type = syringaContext.getSyringaSystemConfig().getType();
        this.topicList = syringaContext.getSyringaSystemConfig().getTopicList();
        this.messageGenerator = syringaContext.getMessageGenerator();
    }

    @Override
    protected void startUp() {

        //转配成作业的配置 
        long threadMessage = new BigDecimal(messages).divide(new BigDecimal(concurrency), 5)
                .longValue();

        for (int j = 0; j < concurrency; j++) {
            Job job = null;
            switch (type) {
                case CONSUMER:
                    job = new ConsumerJob("consumer-job-" + j, threadMessage);
                    break;
                case PRODUCER:
                    job = new ProduceJob(threadMessage);
                    break;
                default:
            }
            job.call();
        }
    }

    @Override
    protected void shutDown() {

    }

}