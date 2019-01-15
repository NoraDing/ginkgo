/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.job.task.JobTask;
import com.bilibili.syringa.core.producer.ProducerWrapper;
import com.bilibili.syringa.core.producer.ProducerWrapperBuilder;
import com.bilibili.syringa.core.statistics.RunResult;
import com.bilibili.syringa.core.statistics.TaskEvent;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProduceJob.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public class ProduceJob extends AbstractJob {

    public ProduceJob(String name, long messageCounter) {
        super(name, messageCounter);
    }

    @Override
    public RunResult call() throws Exception {

        Collection<JobTask> jobTasks = new ArrayList<>(topicList.size());
        for (String topic : topicList) {
            ProducerWrapper instance = ProducerWrapperBuilder.instance(
                syringaSystemConfig.getServers(), topic);
            JobTask jobTask = new JobTask(instance, messageCounter);

            ((ArrayList<JobTask>) jobTasks).add(jobTask);
        }

        List<Future<RunResult>> futures = listeningExecutorService.invokeAll(jobTasks);

        //        for (Future<RunResult> future : futures) {
        //            Futures.addCallback(future, new FutureCallback<V>() {
        //                @Override
        //                public void onSuccess(@Nullable V result) {
        //
        //                    //
        //                    TaskEvent success = new TaskEvent();
        //                    asyncEventBus.post(success);
        //                }
        //
        //                @Override
        //                public void onFailure(Throwable t) {
        //                    TaskEvent failed = new TaskEvent();
        //                    asyncEventBus.post(failed);
        //                }
        //            });
        //        }

        return new RunResult();
    }
}
