/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.properties.Properties;
import com.bilibili.syringa.core.statistics.RunResult;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 *
 * @author dingsainan
 * @version $Id: JobManager.java, v 0.1 2019-01-14 上午11:23 dingsainan Exp $$
 */
public class JobManager extends AbstractIdleService {

    private static final Logger      LOGGER = LoggerFactory.getLogger(JobManager.class);

    private SyringaSystemConfig      syringaSystemConfig;

    private ListeningExecutorService listeningExecutorService;

    private MessageGenerator         messageGenerator;

    private Collection<Job>          runJob = new ArrayList<>();

    public JobManager(SyringaSystemConfig syringaSystemConfig, MessageGenerator messageGenerator) {
        this.syringaSystemConfig = syringaSystemConfig;
        this.messageGenerator = messageGenerator;

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
    }

    @Override
    protected void startUp() throws Exception {

        LOGGER.info("start to init job manager ");

        //转配成作业的配置
        long messages = syringaSystemConfig.getMessages();
        int concurrency = syringaSystemConfig.getConcurrency();
        List<Properties> properties = syringaSystemConfig.getProperties();
        long threadMessage = new BigDecimal(messages).divide(new BigDecimal(concurrency))
            .longValue();

        for (int j = 0; j < concurrency; j++) {
            Job job = null;
            switch (syringaSystemConfig.getType()) {
                case CONSUMER:
                    job = new ConsumerJob("consumer-job-" + j, threadMessage, messageGenerator,
                        properties);
                    break;
                case PRODUCER:
                    job = new ProduceJob("producer-job-" + j, threadMessage, messageGenerator,
                        properties);
                    break;
                default:

            }
            LOGGER.info("submit runjob ");
            runJob.add(job);

        }
    }

    public List<Future<RunResult>> run() throws InterruptedException {

        return listeningExecutorService.invokeAll(runJob);

    }

    @Override
    protected void shutDown() throws Exception {

        listeningExecutorService.shutdown();
    }
}