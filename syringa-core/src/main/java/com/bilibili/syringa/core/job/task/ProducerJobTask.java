/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job.task;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.producer.ProducerWrapper;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProducerJobTask.java, v 0.1 2019-01-15 4:10 PM Exp $$
 */
public class ProducerJobTask implements Callable<RunResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerJobTask.class);
    private static final int    SCALE  = 1024;

    private ProducerWrapper     producerWrapper;
    private long                messageCounter;

    public ProducerJobTask(ProducerWrapper producerWrapper, long messageCounter) {

        this.messageCounter = messageCounter;
        this.producerWrapper = producerWrapper;
    }

    @Override
    public RunResult call() throws Exception {

        RunResult runResult = new RunResult();
        runResult.setTypeEnums(TypeEnums.PRODUCER);

        for (int i = 0; i < messageCounter; i++) {
            producerWrapper.sendMessage(runResult);
        }
        runResult.setSuccess(true);

        //直接输出结果
//        long message = runResult.getMessage();
//        long sizePer = runResult.getSizePer();
//        long totalSize = BigDecimal.valueOf(message).multiply(BigDecimal.valueOf(sizePer))
//            .divide(BigDecimal.valueOf(SCALE)).divide(BigDecimal.valueOf(SCALE)).longValue();
//
//        LocalDateTime startDate = runResult.getStartDate();
//        LocalDateTime finishDate = runResult.getFinishDate();
//        long duration = Duration.between(startDate, finishDate).getSeconds();
//        LOGGER.info("{},{},{}, {}, {}, {}", totalSize, totalSize / duration, message,
//            message / duration);

        return runResult;
    }
}
