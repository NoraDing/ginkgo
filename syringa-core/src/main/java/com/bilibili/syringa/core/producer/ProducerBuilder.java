/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

import com.bilibili.syringa.core.enums.ConfigEnums;

/**
 * @author xuezhaoming
 * @version $Id: ProducerWrapperBuilder.java, v 0.1 2019-01-15 4:03 PM Exp $$
 */
public class ProducerBuilder {

    /**
     * 生产者的建造者模式
     *
     * @return
     */
    public static KafkaProducer instance(Properties properties) {

        properties.put(ConfigEnums.KEY_SERIALIZER_CLASS_CONFIG.getField(),
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ConfigEnums.VALUE_SERIALIZER_CLASS_CONFIG.getField(),
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("linger.ms", 10);
        properties.put("partitioner.class", "com.bilibili.syringa.core.producer.blacklist.BlackListPartitioner");
        properties.put("sasl.kerberos.service.name", "kafka");
        properties.put("sasl.mechanism", "GSSAPI");
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.jaas.config", "com.sun.security.auth.module.Krb5LoginModule required " +
                "useKeyTab=true " +
                "storeKey=true " +
                "keyTab=\"/root/client2.keytab\" " +
                "principal=\"client2@BILIBILI.CO\";");

        return new KafkaProducer<String, String>(properties);
    }
}
