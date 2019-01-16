/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.consumer;

import java.time.LocalDateTime;
import java.util.Collections;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author dingsainan
 * @version $Id: ConsumerWrapper.java, v 0.1 2019-01-14 上午11:22 dingsainan Exp $$
 */
public class ConsumerWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerWrapper.class);

    private KafkaConsumer       kafkaConsumer;

    private String              topic;

    public ConsumerWrapper(KafkaConsumer kafkaConsumer, String topic) {
        this.kafkaConsumer = kafkaConsumer;
        this.topic = topic;

    }

    public void pollMessage(RunResult runResult) {
        if (runResult.getStartDate() == null) {
            runResult.setStartDate(LocalDateTime.now());
        }

        kafkaConsumer.subscribe(Collections.singleton(topic));
        ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
        int count = records.count();
        runResult.setMessage(runResult.getMessage() + count);
        for (ConsumerRecord<String, String> record : records) {
            int size = record.key().getBytes().length;
            runResult.setSizePer(runResult.getSizePer() + size);
        }

        runResult.setFinishDate(LocalDateTime.now());
    }

}