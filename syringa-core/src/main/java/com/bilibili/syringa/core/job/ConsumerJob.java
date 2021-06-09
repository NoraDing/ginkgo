/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bilibili.syringa.core.job.task.ConsumerTask;

/**
 * @author xuezhaoming
 * @version $Id: ConsumerJob.java, v 0.1 2019-01-15 2:32 PM Exp $$
 */
public class ConsumerJob extends AbstractJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerJob.class);
    private long messageCounter;
    private String name;
    private int consumerNumber;

    public ConsumerJob(String name, long messageCounter, int consumerNumber) {
        this.name = name;
        this.messageCounter = messageCounter;
        this.consumerNumber = consumerNumber;
    }

    @Override
    public void call() {
        String groupId = name + consumerNumber + "-syringe-group";
        ConsumerTask consumerTask = new ConsumerTask(groupId, messageCounter, consumerNumber);
        threadPoolExecutor.submit(consumerTask);
    }
}
