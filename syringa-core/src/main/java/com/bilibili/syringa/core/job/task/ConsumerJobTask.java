/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.consumer.ConsumerWrapper;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author dingsainan
 * @version $Id: ConsumerJobTask.java, v 0.1 2019-01-15 下午8:15 dingsainan Exp $$
 */
public class ConsumerJobTask implements Callable<RunResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerJobTask.class);
    private static final int    SCALE  = 1024;

    private ConsumerWrapper     consumerWrapper;
    private long                messageCounter;

    public ConsumerJobTask(ConsumerWrapper consumerWrapper, long messageCounter) {
        this.consumerWrapper = consumerWrapper;
        this.messageCounter = messageCounter;
    }

    @Override
    public RunResult call() throws Exception {
        RunResult runResult = new RunResult();
        runResult.setMessage(messageCounter);
        for (int i = 0; i < messageCounter; i++) {
            consumerWrapper.pollMessage(runResult);
        }

        //直接输出结果
        LocalDateTime startDate = runResult.getStartDate();
        LocalDateTime finishDate = runResult.getFinishDate();
        long message = runResult.getMessage();
        long sizePer = runResult.getSizePer();

        long duration = Duration.between(startDate, finishDate).getSeconds();

        LOGGER.info(
            "start.time, end.time, data.consumed.in.MB, MB.sec, data.consumed.in.nMsg, nMsg.sec");
        LOGGER.info("{},{},{},{}", startDate, finishDate, sizePer / SCALE / SCALE,
            sizePer / SCALE / SCALE / duration, message, message / duration);

        return runResult;
    }
}