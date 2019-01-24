/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

import com.bilibili.syringa.core.enums.ConfigEnums;
import com.bilibili.syringa.core.job.MessageGenerator;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProducerWrapperBuilder.java, v 0.1 2019-01-15 4:03 PM Exp $$
 */
public class ProducerWrapperBuilder {

    /**
     * 生产者的建造者模式
     * @param topic
     * @return
     */
    public static ProducerWrapper instance(String topic, MessageGenerator messageGenerator,
                                           Properties properties) {

        properties.put(ConfigEnums.KEY_SERIALIZER_CLASS_CONFIG.getField(),
            "org.apache.kafka.common.serialization.StringSerializer");

        properties.put(ConfigEnums.VALUE_SERIALIZER_CLASS_CONFIG.getField(),
            "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(properties);

        return new ProducerWrapper(kafkaProducer, topic, messageGenerator);
    }

}
