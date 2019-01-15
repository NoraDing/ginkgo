/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job.task;

import com.bilibili.syringa.core.producer.ProducerWrapper;
import com.bilibili.syringa.core.statistics.RunResult;

import java.util.concurrent.Callable;

/**
 *
 * @author xuezhaoming
 * @version $Id: JobTask.java, v 0.1 2019-01-15 4:10 PM Exp $$
 */
public class JobTask implements Callable<RunResult> {

    private ProducerWrapper producerWrapper;
    private long            messageCounter;

    public JobTask(ProducerWrapper producerWrapper, long messageCounter) {

        this.messageCounter = messageCounter;
        this.producerWrapper = producerWrapper;
    }

    @Override
    public RunResult call() throws Exception {

        for (int i = 0; i < messageCounter; i++) {
            //            producerWrapper.sendMessage();
        }
        return null;
    }
}
