/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProducerWrapperBuilder.java, v 0.1 2019-01-15 4:03 PM Exp $$
 */
public class ProducerWrapperBuilder {

    /**
     * 生产者的建造者模式
     * @param servers kakfa集群地址
     * @param topic
     * @return
     */
    public static ProducerWrapper instance(String servers, String topic) {

        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("acks", "all");
        props.put("retries", 5);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(props);
        return new ProducerWrapper(kafkaProducer, topic);
    }
}
