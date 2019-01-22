/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job.task;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.consumer.ConsumerWrapper;
import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author dingsainan
 * @version $Id: ConsumerJobTask.java, v 0.1 2019-01-15 下午8:15 dingsainan Exp $$
 */
public class ConsumerJobTask implements Callable<RunResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerJobTask.class);

    private ConsumerWrapper     consumerWrapper;
    private long                messageCounter;
    private String              topic;

    public ConsumerJobTask(String topic, ConsumerWrapper consumerWrapper, long messageCounter) {
        this.topic = topic;
        this.consumerWrapper = consumerWrapper;
        this.messageCounter = messageCounter;
    }

    @Override
    public RunResult call() throws Exception {
        RunResult runResult = new RunResult();

        runResult.setTopicName(topic);
        runResult.setTypeEnums(TypeEnums.CONSUMER);
        runResult.setMessage(messageCounter);
        consumerWrapper.getKafkaConsumer().subscribe(Collections.singleton(topic));

        for (int i = 0; i < messageCounter; i++) {
            consumerWrapper.pollMessage(runResult);
        }
        runResult.setSuccess(true);

        return runResult;
    }
}