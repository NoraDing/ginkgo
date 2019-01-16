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
     * @param servers kakfa集群地址
     * @param topic
     * @return
     */
    public static ConsumerWrapper instance(String servers, String topic, String groupId,
                                           Properties properties) {

        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(properties);

        return new ConsumerWrapper(kafkaConsumer, topic);
    }

    //    private static void generateDefaultPro(Properties props) {
    //        props.put(ConfigEnums.ACKS_CONFIG, "all");
    //        props.put(ConfigEnums.GROUP_ID_CONFIG, "consumer_group_id");
    //        props.put(ConfigEnums.ENABLE_AUTO_COMMIT_CONFIG, true);
    //        props.put(ConfigEnums.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
    //        props.put(ConfigEnums.KEY_SERIALIZER_CLASS_CONFIG,
    //            "org.apache.kafka.common.serialization.StringSerializer");
    //        props.put(ConfigEnums.VALUE_SERIALIZER_CLASS_CONFIG,
    //            "org.apache.kafka.common.serialization.StringSerializer");
    //
    //    }

}
