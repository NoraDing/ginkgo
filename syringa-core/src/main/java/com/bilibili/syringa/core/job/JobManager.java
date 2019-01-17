/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.enums.TypeEnums;
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

    private TypeEnums                type;
    private long                     messages;
    private int                      concurrency;
    private List<String>             topicList;
    private Properties               properties;

    private ListeningExecutorService listeningExecutorService;

    private MessageGenerator         messageGenerator;

    private Collection<Job>          runJob = new ArrayList<>();

    public JobManager(TypeEnums type, long messages, int concurrency, List<String> topicList,
                      Properties properties, MessageGenerator messageGenerator) {
        this.type = type;
        this.messages = messages;
        this.concurrency = concurrency;
        this.topicList = topicList;
        this.properties = properties;
        this.messageGenerator = messageGenerator;

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
    }

    @Override
    protected void startUp() throws Exception {

        //转配成作业的配置 
        long threadMessage = new BigDecimal(messages).divide(new BigDecimal(concurrency))
            .longValue();

        for (int j = 0; j < concurrency; j++) {
            Job job = null;
            switch (type) {
                case CONSUMER:
                    job = new ConsumerJob("consumer-job-" + j, threadMessage, messageGenerator,
                        properties);
                    ((ConsumerJob) job).startUp();

                    break;
                case PRODUCER:
                    job = new ProduceJob("producer-job-" + j, threadMessage, messageGenerator,
                        properties, topicList);

                    break;
                default:

            }
            LOGGER.info("submit runjob ");
            runJob.add(job);

        }
    }

    public List<Future<List<RunResult>>> run() throws InterruptedException {

        return listeningExecutorService.invokeAll(runJob);

    }

    @Override
    protected void shutDown() throws Exception {

        listeningExecutorService.shutdown();
    }
}