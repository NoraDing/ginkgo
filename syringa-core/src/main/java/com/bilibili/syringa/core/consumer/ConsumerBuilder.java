/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.consumer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.bilibili.syringa.core.enums.ConfigEnums;

/**
 * @author xuezhaoming
 * @version $Id: ConsumerWrapperBuilder.java, v 0.1 2019-01-15 4:02 PM Exp $$
 */
public class ConsumerBuilder {

    public static KafkaConsumer<String, String> instance(String groupId, Properties properties) {

        properties.put("group.id", groupId);
        properties.put(ConfigEnums.KEY_DESERIALIZER_CLASS_CONFIG.getField(),
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConfigEnums.VALUE_DESERIALIZER_CLASS_CONFIG.getField(),
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("enable.auto.commit", true);
        properties.put("auto.commit.interval.ms", 1000);
        properties.put("auto.offset.reset", "latest");
//
//        properties.put("sasl.kerberos.service.name", "kafka");
//        properties.put("sasl.mechanism", "GSSAPI");
//        properties.put("security.protocol", "SASL_PLAINTEXT");
//        properties.put("sasl.jaas.config", "com.sun.security.auth.module.Krb5LoginModule required " +
//                "useKeyTab=true " +
//                "storeKey=true " +
//                "keyTab=\"/root/client2.keytab\" " +
//                "principal=\"client2@BILIBILI.CO\";");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        return kafkaConsumer;
    }

}
