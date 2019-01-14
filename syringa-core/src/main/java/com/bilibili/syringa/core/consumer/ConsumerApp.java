/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.consumer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

/**
 *
 * @author dingsainan
 * @version $Id: ConsumerApp.java, v 0.1 2019-01-14 上午11:22 dingsainan Exp $$
 */
@Component
public class ConsumerApp {

    //创建Kafka consumer
    private KafkaConsumer createConsumer(String groupId, String bootStrapServer) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootStrapServer);
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", true);
        properties.put("auto.commit.interval.ms", 1000);
        properties.put("key.deserializer",
            "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
            "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(properties);

    }
}