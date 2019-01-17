/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import java.time.LocalDateTime;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.job.MessageGenerator;
import com.bilibili.syringa.core.statistics.RunResult;

/**
 * @author dingsainan
 * @version $Id: ProducerWrapper.java, v 0.1 2019-01-14 上午11:22 dingsainan Exp $$
 */
public class ProducerWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerWrapper.class);

    private KafkaProducer       kafkaProducer;

    private String              topic;

    private MessageGenerator    messageGenerator;

    public ProducerWrapper(KafkaProducer kafkaProducer, String topic,
                           MessageGenerator messageGenerator) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
        this.messageGenerator = messageGenerator;

    }

    public void sendMessage(RunResult runResult) {

        //起始时间
        LocalDateTime startDate = runResult.getStartDate();
        if (startDate == null) {
            runResult.setStartDate(LocalDateTime.now());
        }

        double sizePer = runResult.getSizePer();
        byte[] messageBytes = messageGenerator.getMessage();

        if (messageBytes == null) {
            LOGGER.error("the message byte is null");
            System.exit(-1);

        }
        int size = messageBytes.length;
        runResult.setSizePer(sizePer + size);

        String message = new String(messageBytes);

        kafkaProducer.send(new ProducerRecord<String, String>(topic, message), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                runResult.setFinishDate(LocalDateTime.now());
            }
        });

    }
}