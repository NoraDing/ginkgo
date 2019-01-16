/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import com.bilibili.syringa.core.consumer.ConsumerWrapper;
import com.bilibili.syringa.core.consumer.ConsumerWrapperBuilder;
import com.bilibili.syringa.core.job.task.ConsumerJobTask;
import com.bilibili.syringa.core.properties.Properties;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 * @author xuezhaoming
 * @version $Id: ConsumerJob.java, v 0.1 2019-01-15 2:32 PM Exp $$
 */
public class ConsumerJob extends AbstractJob {

    public ConsumerJob(String name, long messageCounter, MessageGenerator messageGenerator,
                       List<Properties> properties) {
        super(name, messageCounter, messageGenerator, properties);
    }

    @Override
    public RunResult call() throws Exception {

        Collection<ConsumerJobTask> consumerJobTasks = new ArrayList<>(topicList.size());

        for (String topic : topicList) {
            String groupId = topic + "id";

            ConsumerWrapper instance = ConsumerWrapperBuilder
                .instance(syringaSystemConfig.getServers(), topic, groupId, properties);
            ConsumerJobTask consumerJobTask = new ConsumerJobTask(instance, messageCounter);
            consumerJobTasks.add(consumerJobTask);

        }

        List<Future<RunResult>> futures = listeningExecutorService.invokeAll(consumerJobTasks);

        return null;
    }
}
