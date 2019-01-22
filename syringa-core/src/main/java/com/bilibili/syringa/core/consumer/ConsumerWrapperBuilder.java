/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.consumer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.bilibili.syringa.core.enums.ConfigEnums;

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

        properties.put(ConfigEnums.KEY_DESERIALIZER_CLASS_CONFIG.getField(),
            "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConfigEnums.VALUE_DESERIALIZER_CLASS_CONFIG.getField(),
            "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(properties);

        return new ConsumerWrapper(kafkaConsumer, topic);
    }

}
