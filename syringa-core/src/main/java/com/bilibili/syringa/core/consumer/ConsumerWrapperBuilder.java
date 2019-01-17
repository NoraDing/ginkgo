/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.consumer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author xuezhaoming
 * @version $Id: ConsumerWrapperBuilder.java, v 0.1 2019-01-15 4:02 PM Exp $$
 */
public class ConsumerWrapperBuilder {

    /**
     * 生产者的建造者模式
     * @param topic
     * @return
     */
    public static ConsumerWrapper instance(String topic, String groupId, Properties properties) {

        properties.put("group.id", groupId);
        properties.put("key.deserializer",
            "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
            "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(properties);

        return new ConsumerWrapper(kafkaConsumer, topic);
    }

}
