/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.job.task.ConsumerTask;
import com.bilibili.syringa.core.statistics.RunResult;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author xuezhaoming
 * @version $Id: ConsumerJob.java, v 0.1 2019-01-15 2:32 PM Exp $$
 */
public class ConsumerJob extends AbstractJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerJob.class);

    private List<Future<RunResult>> futures;

    private long messageCounter;
    private List<String> topicList;
    private String name;

    public ConsumerJob(String name, long messageCounter) {
        this.name = name;
        this.messageCounter = messageCounter;
        this.topicList = syringaContext.getSyringaSystemConfig().getTopicList();

    }

    @Override
    public void call() {

        List<RunResult> runResults = new ArrayList<>();

        Collection<ConsumerTask> consumerTasks = new ArrayList<>(topicList.size());

        String groupId = name + "group";

        ConsumerTask consumerTask = new ConsumerTask(syringaContext, groupId,
                messageCounter);

        ListenableFuture<RunResult> submitTask = listeningExecutorService.submit(consumerTask);
        Futures.addCallback(submitTask, new FutureCallback<RunResult>() {
            @Override
            public void onSuccess(@Nullable RunResult result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, listeningExecutorService);
    }
}
