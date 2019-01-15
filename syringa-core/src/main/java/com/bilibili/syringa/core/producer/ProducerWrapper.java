/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @author dingsainan
 * @version $Id: ProducerWrapper.java, v 0.1 2019-01-14 上午11:22 dingsainan Exp $$
 */
public class ProducerWrapper {

    private KafkaProducer kafkaProducer;

    private String        topic;

    public ProducerWrapper(KafkaProducer kafkaProducer, String topic) {
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;

    }

}