/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.consumer.ConsumerWrapper;
import com.bilibili.syringa.core.consumer.ConsumerWrapperBuilder;
import com.bilibili.syringa.core.job.task.ConsumerJobTask;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 * @author xuezhaoming
 * @version $Id: ConsumerJob.java, v 0.1 2019-01-15 2:32 PM Exp $$
 */
public class ConsumerJob extends AbstractJob {
    private static final Logger     LOGGER = LoggerFactory.getLogger(ConsumerJob.class);

    private List<Future<RunResult>> futures;

    public ConsumerJob(String name, long messageCounter, MessageGenerator messageGenerator,
                       Properties properties) {
        super(name, messageCounter, messageGenerator, properties, null);
    }

    @Override
    public List<RunResult> call() throws Exception {

        List<RunResult> runResults = new ArrayList<>();
        Collection<ConsumerJobTask> consumerJobTasks = new ArrayList<>(topicList.size());

        for (String topic : topicList) {
            String groupId = topic + "id";

            ConsumerWrapper instance = ConsumerWrapperBuilder.instance(topic, groupId, properties);
            ConsumerJobTask consumerJobTask = new ConsumerJobTask(topic, instance, messageCounter);
            consumerJobTasks.add(consumerJobTask);

        }

        futures = listeningExecutorService.invokeAll(consumerJobTasks);
        if (CollectionUtils.isEmpty(futures)) {
            LOGGER.warn("no valid consumer task can be found");
            return Collections.emptyList();

        }

        for (Future<RunResult> future : futures) {

            //wait all the tasks completed
            RunResult runResult = future.get();
            runResults.add(runResult);
        }
        return runResults;
    }
}
