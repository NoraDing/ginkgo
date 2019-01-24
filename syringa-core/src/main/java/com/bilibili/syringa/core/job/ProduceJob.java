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

import com.bilibili.syringa.core.job.task.ProducerJobTask;
import com.bilibili.syringa.core.producer.ProducerWrapper;
import com.bilibili.syringa.core.producer.ProducerWrapperBuilder;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProduceJob.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public class ProduceJob extends AbstractJob {
    private static final Logger     LOGGER  = LoggerFactory.getLogger(ConsumerJob.class);

    private List<Future<RunResult>> futures = new ArrayList<>();

    public List<Future<RunResult>> getFutures() {
        return futures;
    }

    public ProduceJob(String name, long messageCounter, MessageGenerator messageGenerator,
                      Properties properties, List<String> topicList) {
        super(name, messageCounter, messageGenerator, properties, topicList);
    }

    @Override
    public List<RunResult> call() throws Exception {

        List<RunResult> runResults = new ArrayList<>();

        Collection<ProducerJobTask> producerJobTasks = new ArrayList<>(topicList.size());

        for (String topic : topicList) {

            ProducerWrapper instance = ProducerWrapperBuilder.instance(topic, messageGenerator,
                properties);
            ProducerJobTask producerJobTask = new ProducerJobTask(topic, instance, messageCounter);

            ((ArrayList<ProducerJobTask>) producerJobTasks).add(producerJobTask);
        }

        futures = listeningExecutorService.invokeAll(producerJobTasks);

        //对future进行统计,返回run Result list
        if (CollectionUtils.isEmpty(futures)) {
            LOGGER.warn("no valid producer task can be found");
            return Collections.emptyList();
        }

        for (Future<RunResult> future : futures) {
            RunResult runResult = future.get();
            runResults.add(runResult);

        }
        return runResults;
    }
}
