/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.consumer;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public KafkaConsumer getKafkaConsumer() {
        return kafkaConsumer;
    }

    public ConsumerWrapper(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;

    }

    public long pollMessage(RunResult runResult) {

        if (runResult.getStartDate() == null) {
            runResult.setStartDate(LocalDateTime.now());
        }

        ConsumerRecords<Byte[], Byte[]> records = kafkaConsumer.poll(Duration.ofMillis(1000));
        long count = records.count();
        runResult.setMessage(runResult.getMessage() + count);

        if (count != 0) {
            LOGGER.info("we has value ");

        }
        for (ConsumerRecord<Byte[], Byte[]> record : records) {

            if (record.key() != null) {
                int keySize = record.key().length;
                runResult.setTotalSize(runResult.getTotalSize() + keySize);

            }
            if (record.value() != null) {
                int valueSize = record.value().length;
                runResult.setTotalSize(runResult.getTotalSize() + valueSize);

            }
        }
        runResult.setFinishDate(LocalDateTime.now());

        return count;

    }

}