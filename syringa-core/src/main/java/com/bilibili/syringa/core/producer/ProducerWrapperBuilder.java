/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import java.util.List;
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
                                           List<com.bilibili.syringa.core.properties.Properties> propertiesList) {

        Properties props = new Properties();
        generateDefaultPro(props);
        for (com.bilibili.syringa.core.properties.Properties userProperty : propertiesList) {

            props.put(userProperty.getName(), userProperty.getValue());

        }
        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(props);
        return new ProducerWrapper(kafkaProducer, topic, messageGenerator);
    }

    private static void generateDefaultPro(Properties props) {
        props.put(ConfigEnums.ACKS_CONFIG,"all");
        props.put(ConfigEnums.RETRIES_CONFIG,5);
        props.put(ConfigEnums.BATCH_SIZE_CONFIG,16384);
        props.put(ConfigEnums.LINGER_MS_CONFIG,1);
        props.put(ConfigEnums.BUFFER_MEMORY_CONFIG,33554432);
        props.put(ConfigEnums.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ConfigEnums.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

    }
}
