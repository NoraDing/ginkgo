/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.bilibili.syringa.core.job.MessageGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.producer.ProducerBuilder;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 * @author xuezhaoming
 * @version $Id: ProducerJobTask.java, v 0.1 2019-01-15 4:10 PM Exp $$
 */
public class ProducerTask implements Callable<RunResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerTask.class);
    private long batchSize;
    private KafkaProducer kafkaProducer;
    private long messageCounter;
    private MessageGenerator messageGenerator;
    private String topic;
    private RunResult runResult;
    private long counter;


    public ProducerTask(String topic, long messageCounter) {
        this.topic = topic;
        this.messageCounter = messageCounter;
        this.messageGenerator = SyringaContext.getInstance().getMessageGenerator();
        this.kafkaProducer = ProducerBuilder.instance(
                SyringaContext
                        .getInstance().getSyringaSystemConfig().getProperties());
        this.runResult = new RunResult();
        batchSize = SyringaContext.getInstance().getSyringaSystemConfig().getMaxBatchSize();

    }

    @Override
    public RunResult call() {

        runResult.setTopicName(topic);
        runResult.setTypeEnums(TypeEnums.PRODUCER);
        runResult.setMessage(messageCounter);
        runResult.setStartDate(LocalDateTime.now());
        counter = 0;
        int sendSize = 0;

        try {
            while (messageCounter == -1 ? true : false || counter < messageCounter) {
                while (sendSize < batchSize) {
                    sendSize++;
                    counter++;
                    sendMessage(runResult);
                }
                kafkaProducer.flush();
                sendSize = 0;
            }
            List<RunResult> current = SyringaContext.getInstance().getRunResults();
            current.add(runResult);
        } catch (Exception e) {
            LOGGER.error("has exception", e);
        } finally {
            kafkaProducer.close();
        }
        return runResult;
    }

    public void sendMessage(RunResult runResult) {

        String message = messageGenerator.getMessage();
        if (message == null) {
            LOGGER.error("the message is empty");
            return;
        }
        kafkaProducer.send(new ProducerRecord<String, String>(topic, message), (metadata, exception) -> {
                    if (exception != null) {
                        LOGGER.error("has exception", exception);
                    } else {
                        int size = message.length();
                        runResult.setTotalSize(runResult.getTotalSize() + size);
                        runResult.setFinishDate(LocalDateTime.now());
                    }
                }
        );

        if (CollectionUtils.isNotEmpty(null)){}
    }
}
