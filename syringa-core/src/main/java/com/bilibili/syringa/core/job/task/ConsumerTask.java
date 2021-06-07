/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.bilibili.syringa.core.producer.ProducerBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.consumer.ConsumerBuilder;
import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 * @author dingsainan
 * @version $Id: ConsumerJobTask.java, v 0.1 2019-01-15 下午8:15 dingsainan Exp $$
 */
public class ConsumerTask implements Callable<RunResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTask.class);

    private KafkaConsumer<String, String> kafkaConsumer;
    private long messageCounter;
    private List<String> topics;
    private RunResult runResult;
    private int consumerNumber;

    public ConsumerTask(String groupId, long messageCounter, int consumerNumber) {
        this.messageCounter = messageCounter;
        this.kafkaConsumer = ConsumerBuilder.instance(groupId,
                SyringaContext.getInstance().getSyringaSystemConfig().getProperties());
        this.runResult = new RunResult();
        this.topics = SyringaContext.getInstance().getSyringaSystemConfig().getTopicList();
        this.consumerNumber = consumerNumber;
    }

    @Override
    public RunResult call() {

        LOGGER.info("start consumer :{}", consumerNumber);
        try {
            runResult.setTopicName(String.valueOf(topics));
            runResult.setTypeEnums(TypeEnums.CONSUMER);
            runResult.setMessage(messageCounter);
            kafkaConsumer.subscribe(topics);
            LOGGER.info("the topic is :{},length:{}", topics, topics.size());
            boolean loopAll = messageCounter == -1 ? true : false;
            while (loopAll || messageCounter > 0) {
                long count = pollMessage(runResult);
                messageCounter = messageCounter - count;
                TimeUnit.SECONDS.sleep(10);
            }
            runResult.setSuccess(true);
            List<RunResult> current = SyringaContext.getInstance().getRunResults();
            current.add(runResult);
            LOGGER.info("end consumer :{}", consumerNumber);
        } catch (Exception e) {
            LOGGER.error("consume has exception", e);
        }
        return runResult;
    }


    public long pollMessage(RunResult runResult) {

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10000));
        long count = records.count();
        LOGGER.info("poll record size:{}", count);
        return count;
    }

    public long pollMessageBak(RunResult runResult) {

        if (runResult.getStartDate() == null) {
            runResult.setStartDate(LocalDateTime.now());
        }
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
        long count = records.count();
        runResult.setMessage(runResult.getMessage() + count);

        if (count != 0) {
            LOGGER.info("we has value ");
        }
        for (ConsumerRecord<String, String> record : records) {
            if (record.key() != null) {
                int keySize = record.key().getBytes().length;
                runResult.setTotalSize(runResult.getTotalSize() + keySize);
            }
            if (record.value() != null) {
                int valueSize = record.value().getBytes().length;
                runResult.setTotalSize(runResult.getTotalSize() + valueSize);
            }
        }
        runResult.setFinishDate(LocalDateTime.now());
        return count;
    }
}