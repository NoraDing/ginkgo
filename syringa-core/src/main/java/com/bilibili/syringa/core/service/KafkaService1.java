//package com.bilibili.syringa.core.service;
//
//import com.bilibili.syringa.core.dto.KafkaTopicDTO;
//import kafka.admin.AdminUtils;
//import kafka.server.ConfigType;
//import kafka.utils.ZkUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.kafka.common.security.JaasUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import scala.collection.Seq;
//
//import java.util.Arrays;
//import java.util.Properties;
//
//
//public class KafkaService1 {
//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService1.class);
//    private static String RETENTION_MS = "retention.ms";
//    private static long ONE_DAY_MS = 1000 * 60 * 60 * 24;
//    private static long DEFAULT_DAY = 3;
//
//    private ZkUtils zkUtils;
//
//    public KafkaService1(String zookeeperAddr) {
//        if (zkUtils == null) {
//            zkUtils = getZkUtils(zookeeperAddr);
//        }
//    }
//
//    public ZkUtils getZkUtils(String zookeeperAddr) {
//        ZkUtils zkUtils = ZkUtils.apply(zookeeperAddr, 30000, 30000, JaasUtils.isZkSecurityEnabled());
//        return zkUtils;
//    }
//
//    public void acquireKafkaTopicConfigInfo(KafkaTopicDTO kafkaTopicDTO) {
//        String topic = kafkaTopicDTO.getTopic();
//        Properties properties = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
//        long retentionDay = StringUtils.isBlank(properties.getProperty(RETENTION_MS)) ? DEFAULT_DAY : Long.parseLong(properties.getProperty(RETENTION_MS)) / ONE_DAY_MS;
//        Seq<String> topics = scala.collection.JavaConversions.asScalaBuffer(Arrays.asList(topic));
//        int size = zkUtils.getPartitionsForTopics(topics).apply(topic).size();
//        kafkaTopicDTO.setPartition(size);
//        kafkaTopicDTO.setRetentionDay((int) retentionDay);
//    }
//}
