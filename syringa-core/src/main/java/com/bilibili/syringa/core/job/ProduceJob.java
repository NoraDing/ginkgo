/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

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
    private static final Logger     LOGGER = LoggerFactory.getLogger(ConsumerJob.class);

    private List<Future<RunResult>> futures;

    public List<Future<RunResult>> getFutures() {
        return futures;
    }

    public ProduceJob(String name, long messageCounter, MessageGenerator messageGenerator,
                      Properties properties) {
        super(name, messageCounter, messageGenerator, properties);
    }

    @Override
    public RunResult call() throws Exception {

        RunResult runResult = new RunResult();

        Collection<ProducerJobTask> producerJobTasks = new ArrayList<>(topicList.size());
//
//        LOGGER.info("start.time, end.time,total.data.sent.in.MB, MB.sec, "
//                    + "total.data.sent.in.nMsg, nMsg.sec");

        for (String topic : topicList) {
            ProducerWrapper instance = ProducerWrapperBuilder.instance(topic, messageGenerator,
                properties);
            ProducerJobTask producerJobTask = new ProducerJobTask(instance, messageCounter);

            ((ArrayList<ProducerJobTask>) producerJobTasks).add(producerJobTask);
        }

        futures = listeningExecutorService.invokeAll(producerJobTasks);

        return runResult;
    }
}
