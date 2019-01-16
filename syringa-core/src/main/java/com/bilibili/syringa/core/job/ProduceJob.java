/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import com.bilibili.syringa.core.job.task.ProducerJobTask;
import com.bilibili.syringa.core.producer.ProducerWrapper;
import com.bilibili.syringa.core.producer.ProducerWrapperBuilder;
import com.bilibili.syringa.core.properties.Properties;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProduceJob.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public class ProduceJob extends AbstractJob {

    public ProduceJob(String name, long messageCounter, MessageGenerator messageGenerator,
                      List<Properties> properties) {
        super(name, messageCounter, messageGenerator, properties);
    }

    @Override
    public RunResult call() throws Exception {

        RunResult runResult = new RunResult();

        Collection<ProducerJobTask> producerJobTasks = new ArrayList<>(topicList.size());
        for (String topic : topicList) {
            ProducerWrapper instance = ProducerWrapperBuilder.instance(topic, messageGenerator,
                properties);
            ProducerJobTask producerJobTask = new ProducerJobTask(instance, messageCounter);

            ((ArrayList<ProducerJobTask>) producerJobTasks).add(producerJobTask);
        }

        List<Future<RunResult>> futures = listeningExecutorService.invokeAll(producerJobTasks);
        //
        //        for (Future<RunResult> future : futures) {
        //            Futures.addCallback(future, new FutureCallback<RunResult>() {
        //
        //                @Override
        //                public void onSuccess(@Nullable RunResult result) {
        //
        //                }
        //
        //                @Override
        //                public void onFailure(Throwable t) {
        //
        //                }
        //            });
        //        }

        return runResult;
    }
}
