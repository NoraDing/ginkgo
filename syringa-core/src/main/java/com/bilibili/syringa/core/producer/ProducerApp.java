/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;

/**
 *
 * @author dingsainan
 * @version $Id: ProducerApp.java, v 0.1 2019-01-14 上午11:22 dingsainan Exp $$
 */
@Component
public class ProducerApp {

    //创建Kafka producer
    public KafkaProducer createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.23.58.141:9094");
        props.put("acks", "all");
        props.put("retries", 5);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(props);

        return kafkaProducer;

    }
}