/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import com.bilibili.syringa.core.Syringa;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.statistics.RunResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * @author dingsainan
 * @version $Id: JobManager.java, v 0.1 2019-01-14 上午11:23 dingsainan Exp $$
 */
public class JobManager extends AbstractIdleService {

    private static final Logger      LOGGER = LoggerFactory.getLogger(JobManager.class);

    private SyringaSystemConfig      syringaSystemConfig;

    private ListeningExecutorService listeningExecutorService;

    private Collection<Job>          runJob = new ArrayList<>();

    public JobManager(SyringaSystemConfig syringaSystemConfig) {
        this.syringaSystemConfig = syringaSystemConfig;

        listeningExecutorService = MoreExecutors.newDirectExecutorService();
    }

    @Override
    protected void startUp() throws Exception {

        LOGGER.info("start to init job manager ");

        //转配成作业的配置
        int branches = syringaSystemConfig.getBranches();
        long messages = syringaSystemConfig.getMessages();
        int concurrency = syringaSystemConfig.getConcurrency();
        long threadMessage = new BigDecimal(messages).divide(new BigDecimal(concurrency))
            .longValue();

        Collection<ListenableFuture> listenableFutureList = new ArrayList<>();

        for (int i = 0; i < branches; i++) {

            for (int j = 0; j < concurrency; j++) {
                Job job = null;
                switch (syringaSystemConfig.getType()) {
                    case CONSUMER:
                        job = new ConsumerJob("consumer-job-" + i, threadMessage);
                        break;
                    case PRODUCER:
                        job = new ProduceJob("producer-job-" + i, threadMessage);
                        System.out.println();
                    default:

                }
                LOGGER.info("submit runjob ");
                runJob.add(job);

            }
        }

        //运行作业
    }

    public List<Future<RunResult>> run() throws InterruptedException {

        return listeningExecutorService.invokeAll(runJob);

    }

    @Override
    protected void shutDown() throws Exception {

        listeningExecutorService.shutdown();
    }
}